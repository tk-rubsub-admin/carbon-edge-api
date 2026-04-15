package th.co.carbonedge.api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThaiBahtText {

    private static final String[] NUMBER_TEXT = {
            "", "หนึ่ง", "สอง", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด", "เก้า"
    };
    private static final String[] POSITION_TEXT = {
            "", "สิบ", "ร้อย", "พัน", "หมื่น", "แสน", "ล้าน"
    };

    public static String convertBahtText(double amount) {
        if (amount == 0) {
            return "ศูนย์บาทถ้วน";
        }

        StringBuilder result = new StringBuilder();
        String[] parts = String.format("%.2f", amount).split("\\.");

        String baht = convertIntegerPartToThaiText(parts[0]);
        result.append(baht).append("บาท");

        int satang = Integer.parseInt(parts[1]);
        if (satang == 0) {
            result.append("ถ้วน");
        } else {
            result.append(convertIntegerPartToThaiText(parts[1])).append("สตางค์");
        }

        return result.toString();
    }

    private static String convertIntegerPartToThaiText(String number) {
        StringBuilder result = new StringBuilder();

        int len = number.length();
        for (int i = 0; i < len; i++) {
            int digit = Character.getNumericValue(number.charAt(i));
            int position = len - i - 1;

            if (digit == 0) continue;

            if (position == 0 && digit == 1 && len > 1) {
                result.append("เอ็ด");
            } else if (position == 1 && digit == 2) {
                result.append("ยี่");
                result.append(POSITION_TEXT[position]);
            } else if (position == 1 && digit == 1) {
                result.append(POSITION_TEXT[position]);
            } else {
                result.append(NUMBER_TEXT[digit]);
                result.append(POSITION_TEXT[position]);
            }

            // Handle ล้าน
            if (position == 6 && i != len - 1) {
                result.append("ล้าน");
            }
        }

        return result.toString();
    }
}