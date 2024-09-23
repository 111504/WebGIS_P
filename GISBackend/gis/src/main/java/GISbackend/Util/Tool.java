package GISbackend.Util;

public class Tool {
    public static String convertParcelnoToLandNumber(String parcelNumber) {
        // 檢查是否包含 "-"
        if (parcelNumber.contains("-")) {
            String[] parts = parcelNumber.split("-");
            String leftPart = String.format("%04d", Integer.parseInt(parts[0]));  // 處理左邊部分
            String rightPart = String.format("%04d", Integer.parseInt(parts[1])); // 處理右邊部分
            return leftPart + rightPart;
        } else {
            // 如果沒有 "-"，右邊補零到 8 位數
            return String.format("%04d", Integer.parseInt(parcelNumber)) + "0000";
        }
    }


    public static String convertLandNumberToParcelNumber(String landNumber) {
        // 檢查輸入是否為null
        if (landNumber == null || landNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("landNumber 不能为 null 或空");
        }

        // 移除任何非數字字元
        landNumber = landNumber.replaceAll("\\D", "");

        // 確保landNumber 長度為8位
        if (landNumber.length() != 8) {
            throw new IllegalArgumentException("landNumber 必须是 8 位数字");
        }

        // 分割為左邊4位 右邊4位
        String leftPart = landNumber.substring(0, 4);
        String rightPart = landNumber.substring(4, 8);


        if (rightPart.equals("0000")) {

            String parcel = String.valueOf(Integer.parseInt(leftPart));
            return parcel;
        } else {

            String parcelLeft = String.valueOf(Integer.parseInt(leftPart));
            String parcelRight = String.valueOf(Integer.parseInt(rightPart));
            return parcelLeft + "-" + parcelRight;
        }
    }
}
