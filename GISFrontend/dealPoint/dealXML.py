import psycopg2
from lxml import etree

# 連接到 PostgreSQL 資料庫
conn = psycopg2.connect(
    dbname="gis", user="postgres", password="1234", host="localhost", port="5432"
)
cur = conn.cursor()

# 解析 XML 檔案
def parse_xml(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        tree = etree.parse(file)
        root = tree.getroot()
        
        # 找到所有的土地標示部標籤
        lands = root.findall('.//土地標示部')

        for land in lands:
            county = land.find('縣市').text
            town = land.find('鄉鎮市區').text
            section_code = land.find('段代碼').text
            section_name = land.find('段小段').text
            land_number = land.find('地號').text
            area = float(land.find('登記面積').text)
            market_value = int(land.find('公告現值').text)
            land_value_text = land.find('公告地價').text
            # 檢查如果為空字串，則設置為 0，否則轉換為 int
            land_value = int(float(land_value_text)) if land_value_text else 0
            
            # 所有權人資訊
            owner = land.find('所有權人')
            owner_name = owner.find('所有權人名稱').text
            owner_type = owner.find('所有權人類別').text
            owner_id = owner.find('統一編號').text
            owner_share_numerator = int(owner.find('權利範圍持分分子').text)
            owner_share_denominator = int(owner.find('權利範圍持分分母').text)
            manager_name = owner.find('管理者名稱').text if owner.find('管理者名稱') is not None else None
            
            # 插入到 PostgreSQL
            insert_into_postgres(county, town, section_code, section_name, land_number, area, market_value, land_value, owner_name, owner_type, owner_id, owner_share_numerator, owner_share_denominator, manager_name)

# 將解析後的資料插入 PostgreSQL
def insert_into_postgres(county, town, section_code, section_name, land_number, area, market_value, land_value, owner_name, owner_type, owner_id, owner_share_numerator, owner_share_denominator, manager_name):
    insert_query = """
        INSERT INTO lands (county, town, section_code, section_name, land_number, area, market_value, land_value, owner_name, owner_type, owner_id, owner_share_numerator, owner_share_denominator, manager_name)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """
    cur.execute(insert_query, (county, town, section_code, section_name, land_number, area, market_value, land_value, owner_name, owner_type, owner_id, owner_share_numerator, owner_share_denominator, manager_name))
    conn.commit()

# 運行解析與插入過程
parse_xml('D:\\GitHub_P\\webGIs\\D\\d_0072.xml')

# 關閉連接
cur.close()
conn.close()
