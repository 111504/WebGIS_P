import os
import psycopg2
from lxml import etree
from shapely.geometry import Polygon

# 連接到 PostgreSQL 資料庫
conn = psycopg2.connect(
    dbname="gis", user="postgres", password="1234", host="localhost", port="5432"
)
cur = conn.cursor()

# 解析 KML 檔案
def parse_kml(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            tree = etree.parse(file)
            root = tree.getroot()

            # 定義 KML 的命名空間
            ns = {'kml': 'http://www.opengis.net/kml/2.2'}

            # 找到所有的 Placemark 標籤
            placemarks = root.findall('.//kml:Placemark', namespaces=ns)

            for placemark in placemarks:
                name_elem = placemark.find('kml:name', namespaces=ns)
                name = name_elem.text if name_elem is not None else 'Unknown'

                # 解析 SECTNO 和 PARCELNO
                sectno_elem = placemark.find('.//kml:SimpleData[@name="SECTNO"]', namespaces=ns)
                parcelno_elem = placemark.find('.//kml:SimpleData[@name="PARCELNO"]', namespaces=ns)
                sectno = sectno_elem.text if sectno_elem is not None else 'Unknown'
                parcelno = parcelno_elem.text if parcelno_elem is not None else 'Unknown'


                # 找到多邊形的座標並轉換為 list
                coordinates_elem = placemark.find('.//kml:coordinates', namespaces=ns)
                if coordinates_elem is not None and coordinates_elem.text:
                    coordinates_text = coordinates_elem.text.strip()
                    coordinates = [
                        tuple(map(float, coord.split(',')))[:2]  # 只取經緯度，忽略高度
                        for coord in coordinates_text.split()
                    ]

                    # 使用 Shapely 建立 Polygon
                    if len(coordinates) >= 3:  # 確保座標數量足夠形成多邊形
                        polygon = Polygon(coordinates)
                        # 將資料插入 PostgreSQL
                        insert_into_postgis(name, sectno, parcelno, polygon)
                    else:
                        print(f"檔案 {file_path} 中的 Placemark {name} 座標數量不足，無法形成多邊形。")
                else:
                    print(f"檔案 {file_path} 中的 Placemark {name} 找不到座標。")
    except Exception as e:
        print(f"解析 {file_path} 時發生錯誤: {e}")

# 插入資料到 PostGIS
def insert_into_postgis(name, sectno, parcelno, polygon):
    geom_wkt = polygon.wkt  # 轉換為 WKT 格式 (Well-Known Text)

    insert_query = """
        INSERT INTO kml_data (name, sectno, parcelno, geom)
        VALUES (%s, %s, %s, ST_GeomFromText(%s, 4326))
    """
    try:
        cur.execute(insert_query, (name, sectno, parcelno, geom_wkt))
        conn.commit()
        print(f"成功插入: {name}, SECTNO: {sectno}, PARCELNO: {parcelno}")
    except Exception as e:
        print(f"插入資料時發生錯誤: {e}")


# 解析資料夾中的所有 KML 檔案
def parse_all_kml_files(directory):
    for filename in os.listdir(directory):
        if filename.endswith('.kml'):
            file_path = os.path.join(directory, filename)
            parse_kml(file_path)


# 設定 KML 檔案所在的資料夾路徑
directory = 'C:\\Users\\user\\Desktop\\WebInfo\\D_M_KML'


# 呼叫函數解析所有 KML 檔案
parse_all_kml_files(directory)

# 關閉連接
cur.close()
conn.close()
