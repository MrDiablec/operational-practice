import java.util.HashMap;
import java.util.Map;

public class ScholarshipSettings {
    private Map<String, Double> baseAmounts;
    private double bonusPercentage;

    public ScholarshipSettings() {
        baseAmounts = new HashMap<>();
        baseAmounts.put("Академическая (4.0-4.49)", 5000.0);
        baseAmounts.put("Академическая (4.5+)", 7500.0);
        baseAmounts.put("Повышенная", 10000.0);
        baseAmounts.put("Социальная", 3000.0);
        bonusPercentage = 0.1;
    }

    public double getBaseAmount(String type, double grade) {
        if ("Академическая".equals(type)) {
            if (grade >= 4.5) return baseAmounts.get("Академическая (4.5+)");
            else if (grade >= 4.0) return baseAmounts.get("Академическая (4.0-4.49)");
            else return 0;
        } else {
            return baseAmounts.getOrDefault(type, 0.0);
        }
    }

    public double getBonusPercentage() {
        return bonusPercentage;
    }
}
