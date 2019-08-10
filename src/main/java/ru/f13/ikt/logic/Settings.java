package ru.f13.ikt.logic;

/**
 * Класс описывает настройки ограничений
 */
public class Settings {

    private boolean isLimitedTime;
    private String startLimitedTimeValue;
    private String endLimitedTimeValue;

    private boolean isCompanyWorkflowLimit;
    private int companyWorkflowLimitValue;

    private boolean isCompanyCreateDocumentLimit;
    private int companyCreateDocumentLimitValue;
    private int companyCreateDocumentLimitedTimeValue;

    private boolean isWorkflowLimit;
    private int workflowLimitValue;

    private Settings() {
    }

    /**
     * Создать настройки с дефолтными значениями
     * @return объект настроек
     */
    public static Settings createDefaultSettings() {
        return createCustomSettings(true, "07:00", "21:00", true, 10, true, 10, 1, true, 10);
    }

    /**
     * Создать настройки с настраиваемыми значениями
     * @param isLimitedTime ограничения по времени  true - подтверждены, false - не подтверждены
     * @param startLimitedTimeValue стартовое значение ограничения времени
     * @param endLimitedTimeValue конечное значение ограничения времени
     * @param isCompanyWorkflowLimit ограничения по документообороту компании  true - подтверждены, false - не подтверждены
     * @param companyWorkflowLimitValue значение ограничений по документообороту компании
     * @param isCompanyCreateDocumentLimit ограничения по созданию документов компанией true - подтверждены, false - не подтверждены
     * @param companyCreateDocumentLimitValue значение ограничения по созданию документов компанией
     * @param companyCreateDocumentLimitedTimeValue значение ограничения по созданию документов компанией по времени (в часах)
     * @param isWorkflowLimit ограничения по документообороту компаний true - подтверждены, false - не подтверждены
     * @param workflowLimitValue значение ограничения по документообороту компаний
     * @return объект настроек {@link Settings}
     */
    public static Settings createCustomSettings(
            boolean isLimitedTime,
            String startLimitedTimeValue,
            String endLimitedTimeValue,
            boolean isCompanyWorkflowLimit,
            int companyWorkflowLimitValue,
            boolean isCompanyCreateDocumentLimit,
            int companyCreateDocumentLimitValue,
            int companyCreateDocumentLimitedTimeValue,
            boolean isWorkflowLimit,
            int workflowLimitValue
    ) {

        Settings settings = new Settings();

        settings.setLimitedTime(isLimitedTime);
        settings.setStartLimitedTimeValue(startLimitedTimeValue);
        settings.setEndLimitedTimeValue(endLimitedTimeValue);

        settings.setCompanyWorkflowLimit(isCompanyWorkflowLimit);
        settings.setCompanyWorkflowLimitValue(companyWorkflowLimitValue);

        settings.setCompanyCreateDocumentLimit(isCompanyCreateDocumentLimit);
        settings.setCompanyCreateDocumentLimitValue(companyCreateDocumentLimitValue);
        settings.setCompanyCreateDocumentLimitedTimeValue(companyCreateDocumentLimitedTimeValue);

        settings.setWorkflowLimit(isWorkflowLimit);
        settings.setWorkflowLimitValue(workflowLimitValue);

        return settings;
    }

    /**
     * Проверить ограничения по времени
     * @return true - подтверждены, false - не подтверждены
     */
    public boolean isLimitedTime() {
        return isLimitedTime;
    }

    /**
     * Установить ограничения по времени
     * @param limitedTime true - подтверждены, false - не подтверждены
     */
    public void setLimitedTime(boolean limitedTime) {
        isLimitedTime = limitedTime;
    }

    /**
     * Получить стартовое значение ограничения времени
     * @return время суток "00:00" - "23:59"
     */
    public String getStartLimitedTimeValue() {
        return startLimitedTimeValue;
    }

    /**
     * Установить стартовое значение ограничения времени
     * @param startLimitedTimeValue время суток "00:00" - "23:59"
     */
    public void setStartLimitedTimeValue(String startLimitedTimeValue) {
        this.startLimitedTimeValue = startLimitedTimeValue;
    }

    /**
     * Получить конечное значение ограничения времени
     * @return время суток "00:00" - "23:59"
     */
    public String getEndLimitedTimeValue() {
        return endLimitedTimeValue;
    }

    /**
     * Установить конечное значение ограничения времени
     * @param endLimitedTimeValue время суток "00:00" - "23:59"
     */
    public void setEndLimitedTimeValue(String endLimitedTimeValue) {
        this.endLimitedTimeValue = endLimitedTimeValue;
    }

    /**
     * Проверить ограничения по документообороту компании
     * @return true - подтверждены, false - не подтверждены
     */
    public boolean isCompanyWorkflowLimit() {
        return isCompanyWorkflowLimit;
    }

    /**
     * Установить ограничения по документообороту компании
     * @param companyWorkflowLimit true - подтверждены, false - не подтверждены
     */
    public void setCompanyWorkflowLimit(boolean companyWorkflowLimit) {
        isCompanyWorkflowLimit = companyWorkflowLimit;
    }

    /**
     * Получить значение ограничения по документообороту компании
     * @return значение ограничения по документообороту компании
     */
    public int getCompanyWorkflowLimitValue() {
        return companyWorkflowLimitValue;
    }

    /**
     * Установить значение ограничения по документообороту компании
     * @param companyWorkflowLimitValue значение ограничения по документообороту компании
     */
    public void setCompanyWorkflowLimitValue(int companyWorkflowLimitValue) {
        this.companyWorkflowLimitValue = companyWorkflowLimitValue;
    }

    /**
     * Проверить ограничения по созданию документов компанией
     * @return true - подтверждены, false - не подтверждены
     */
    public boolean isCompanyCreateDocumentLimit() {
        return isCompanyCreateDocumentLimit;
    }

    /**
     * Установить ограничения по созданию документов компанией
     * @param companyCreateDocumentLimit true - подтверждены, false - не подтверждены
     */
    public void setCompanyCreateDocumentLimit(boolean companyCreateDocumentLimit) {
        isCompanyCreateDocumentLimit = companyCreateDocumentLimit;
    }

    /**
     * Получить значение ограничения по созданию документов компанией
     * @return значение ограничения по созданию документов компанией
     */
    public int getCompanyCreateDocumentLimitValue() {
        return companyCreateDocumentLimitValue;
    }

    /**
     * Установить значение ограничения по созданию документов компанией
     * @param companyCreateDocumentLimitValue значение ограничения по созданию документов компанией
     */
    public void setCompanyCreateDocumentLimitValue(int companyCreateDocumentLimitValue) {
        this.companyCreateDocumentLimitValue = companyCreateDocumentLimitValue;
    }

    /**
     * Получить значение ограничения по созданию документов компанией по времени
     * @return время в часах
     */
    public int getCompanyCreateDocumentLimitedTimeValue() {
        return companyCreateDocumentLimitedTimeValue;
    }

    /**
     * Установить значение ограничения по созданию документов компанией по времени
     * @param companyCreateDocumentLimitedTimeValue время в часах
     */
    public void setCompanyCreateDocumentLimitedTimeValue(int companyCreateDocumentLimitedTimeValue) {
        this.companyCreateDocumentLimitedTimeValue = companyCreateDocumentLimitedTimeValue;
    }

    /**
     * Проверить ограничения по документообороту компаний
     * @return true - подтверждены, false - не подтверждены
     */
    public boolean isWorkflowLimit() {
        return isWorkflowLimit;
    }

    /**
     * Установить ограничения по документообороту компаний
     * @param workflowLimit  true - подтверждены, false - не подтверждены
     */
    public void setWorkflowLimit(boolean workflowLimit) {
        isWorkflowLimit = workflowLimit;
    }

    /**
     * Получить значение ограничения по документообороту компаний
     * @return значение ограничения по документообороту компаний
     */
    public int getWorkflowLimitValue() {
        return workflowLimitValue;
    }

    /**
     * Установить значение ограничения по документообороту компаний
     * @param workflowLimitValue значение ограничения по документообороту компаний
     */
    public void setWorkflowLimitValue(int workflowLimitValue) {
        this.workflowLimitValue = workflowLimitValue;
    }

}
