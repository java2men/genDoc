package ru.f13.ikt;

import org.junit.Assert;
import org.junit.Test;
import ru.f13.ikt.logic.*;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    /**
     * Попасть в ограниченное время (покрыть ограниченным временем сутки)
     */
    @Test
    public void getToLimitedTime() {

        SystemWorkflow systemWorkflow = new SystemWorkflow();
        systemWorkflow.setSettings(Settings.createCustomSettings(
                true, "00:00", "23:59",
                true, 10,
                true, 10, 1,
                true, 10)
        );

        Company1 company1 = new Company1();
        Company2 company2 = new Company2();

        boolean actual = systemWorkflow.addDocument1(company1.createDocument(company2));

        Assert.assertFalse(actual);
    }

    /**
     * Не превышать лимит документооборотов компании
     */
    @Test
    public void doNotExceedCompanyWorkflowLimit() {

        int limitCompanyWorkflow = 10;

        SystemWorkflow systemWorkflow = new SystemWorkflow();
        systemWorkflow.setSettings(Settings.createCustomSettings(
                true, "00:00", "00:01",
                true, limitCompanyWorkflow,
                true, 10, 1,
                true, 10)
        );

        Company1 company1 = new Company1();
        Company2 company2 = new Company2();

        //добавить документов на 1 меньше допустимого
        for (int i = 0; i < limitCompanyWorkflow - 1; i++) {
            systemWorkflow.addDocument1(company1.createDocument(company2));
        }

        boolean actual = systemWorkflow.addDocument1(company1.createDocument(company2));

        Assert.assertTrue(actual);
    }

    /**
     * Превысить лимит созданных документов компанией
     */
    @Test
    public void exceedCompanyCreateDocumentLimit() {

        int limit = 10;

        SystemWorkflow systemWorkflow = new SystemWorkflow();
        systemWorkflow.setSettings(Settings.createCustomSettings(
                true, "00:00", "00:01",
                true, 10,
                true, limit, 1,
                true, 10)
        );

        Company1 company1 = new Company1();
        Company2 company2 = new Company2();

        //создать документов с превышением ограничения
        for (int i = 0; i < limit; i++) {
            company1.createDocument(company2);
        }

        boolean actual = systemWorkflow.addDocument1(company1.createDocument(company2));

        Assert.assertFalse(actual);
    }

    /**
     * Превысить количество допустимых документоборотов между компаниями
     */
    @Test
    public void exceedWorkflowLimit() {

        int limit = 10;

        SystemWorkflow systemWorkflow = new SystemWorkflow();
        systemWorkflow.setSettings(Settings.createCustomSettings(
                true, "00:00", "00:01",
                true, 10,
                true, 10, 1,
                true, limit)
        );

        Company1 company1 = new Company1();
        Company2 company2 = new Company2();

        //добавить документов с превышением ограничения
        for (int i = 0; i < limit; i++) {
            Document document = company1.createDocument(company2);
            systemWorkflow.addDocument1(document);
            //сбросить подписания
            document.resetSigning();

        }

        boolean actual = systemWorkflow.addDocument1(company1.createDocument(company2));

        Assert.assertFalse(actual);
    }

    /**
     * Превысить значения ограничений и выключить проверку ограничений
     */
    @Test
    public void toExceedLimitValuesAndDisableLimitChecking() {

        SystemWorkflow systemWorkflow = new SystemWorkflow();

        Company1 company1 = new Company1();
        Company2 company2 = new Company2();

        systemWorkflow.setSettings(Settings.createCustomSettings(
                false, "00:00", "23:59",
                false, 10,
                false, 10, 1,
                false, 10)
        );

        boolean actual = systemWorkflow.addDocument1(company1.createDocument(company2));

        Assert.assertTrue(actual);

        //добавить документов с превышением ограничения
        for (int i = 0; i < 11; i++) {
            Document document = company1.createDocument(company2);
            systemWorkflow.addDocument1(document);

        }

        actual = systemWorkflow.addDocument1(company1.createDocument(company2));

        Assert.assertTrue(actual);

        //создать документов с превышением ограничения
        for (int i = 0; i < 11; i++) {
            company1.createDocument(company2);
        }

        actual = systemWorkflow.addDocument1(company1.createDocument(company2));

        Assert.assertTrue(actual);

        //добавить документов с превышением ограничения
        for (int i = 0; i < 11; i++) {
            Document document = company1.createDocument(company2);
            systemWorkflow.addDocument1(document);
            //сбросить подписания
            document.resetSigning();

        }

        actual = systemWorkflow.addDocument1(company1.createDocument(company2));

        Assert.assertTrue(actual);
    }

    /**
     * Тест примера сценария 2, без системы и ограничений
     */
    @Test
    public void testScenario2WithoutSystemAndLimits() {

        Company1 company1 = new Company1();
        Company2 company2 = new Company2();

        Document document = company1.createDocument(company2);
        document.sign(company1);

        if (document.isSigning(company1)) {

            //номер итерации на которой компания подписала документ
//            int transfers1 = Integer.MAX_VALUE;
            int transfers1 = 10000;

            int i = -1;
            while (!document.isSigning()) {
                i++;

                company1.transferDocument(document, company2);
                company2.changeDocument(document);
                document.sign(company2);

                if (document.isSigning(company2)) {
                    company2.transferDocument(document, company1);

                    if (i == transfers1) {
                        document.sign(company1);
                    }

                }

            }
        }

        Assert.assertTrue(document.isSigning());

    }

}
