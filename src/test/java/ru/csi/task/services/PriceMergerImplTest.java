package ru.csi.task.services;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.csi.task.model.Price;
import ru.csi.task.utils.Utils;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.time.Month;
import java.util.*;


public class PriceMergerImplTest {

    // Устанавливаем кодировку, затем подставляем рефлексией в поле кодировки null, таким образом JVM заново
    // сама проставляет кодировку по умолчанию. Помогло :)
    // Только непонятно с чем это связано...
    @BeforeClass
    public static void setUp() throws NoSuchFieldException, IllegalAccessException {
        System.out.print("");
        System.setProperty("file.encoding","UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null,null);
        System.out.println("Кодировка установлена");

    }

    PriceMergerImpl prs = new PriceMergerImpl();

    Price price = new Price((long) 0, "122856", 1, 1, Utils.getStartDate(Month.JANUARY, 1), Utils.getEndDate(Month.JANUARY, 31), (long) 11000);
    Price price1 = new Price((long) 0, "122856", 2, 1, Utils.getStartDate(Month.JANUARY, 10), Utils.getEndDate(Month.JANUARY, 20), (long) 99000);
    Price price12 = new Price((long) 0, "6654", 1, 2, Utils.getStartDate(Month.JANUARY, 1), Utils.getEndDate(Month.JANUARY, 31), (long) 5000);

    Price price2 = new Price((long) 0, "122856", 1, 1, Utils.getStartDate(Month.JANUARY, 20), Utils.getEndDate(Month.FEBRUARY, 20), (long) 11000);
    Price price21 = new Price((long) 0, "122856", 2, 1, Utils.getStartDate(Month.JANUARY, 15), Utils.getEndDate(Month.JANUARY, 25), (long) 92000);
    Price price22 = new Price((long) 0, "6654", 1, 2, Utils.getStartDate(Month.JANUARY, 12), Utils.getEndDate(Month.JANUARY, 13), (long) 4000);

    Price price01 = new Price((long) 0, "122856", 1, 1, Utils.getStartDate(Month.JANUARY, 1), Utils.getEndDate(Month.FEBRUARY, 20), (long) 11000);
    Price price02 = new Price((long) 0, "122856", 2, 1, Utils.getStartDate(Month.JANUARY, 10), Utils.getEndDate(Month.JANUARY, 14), (long) 99000);
    Price price03 = new Price((long) 0, "122856", 2, 1, Utils.getStartDate(Month.JANUARY, 15), Utils.getEndDate(Month.JANUARY, 25), (long) 92000);
    Price price04 = new Price((long) 0, "6654", 1, 2, Utils.getStartDate(Month.JANUARY, 1), Utils.getEndDate(Month.JANUARY, 11), (long) 5000);
    Price price05 = new Price((long) 0, "6654", 1, 2, Utils.getStartDate(Month.JANUARY, 14), Utils.getEndDate(Month.JANUARY, 31), (long) 5000);
    Price price06 = new Price((long) 0, "6654", 1, 2, Utils.getStartDate(Month.JANUARY, 12), Utils.getEndDate(Month.JANUARY, 13), (long) 4000);

    // Пример из ТЗ
    @Test
    public void exampleTest() {

        ArrayList<Price> oldlst = new ArrayList<Price>();
        ArrayList<Price> newlst = new ArrayList<Price>();
        ArrayList<Price> checklist = new ArrayList<Price>();
        ArrayList<Price> endlist = new ArrayList<Price>();
        oldlst.add(price);
        oldlst.add(price1);
        oldlst.add(price12);

        newlst.add(price2);
        newlst.add(price21);
        newlst.add(price22);

        checklist.add(price01);
        checklist.add(price02);
        checklist.add(price03);
        checklist.add(price04);
        checklist.add(price05);
        checklist.add(price06);

        endlist = prs.mergePrices(oldlst, newlst);
        Assert.assertEquals(endlist, checklist);
        System.out.println("Список цен из примера протестирован, ниже конечный результат:\n"+ endlist);

    }

    @Test
    public void newNullTest() {
        ArrayList<Price> oldlst = new ArrayList<Price>();
        ArrayList<Price> endlist = new ArrayList<Price>();

        oldlst.add(price);
        oldlst.add(price1);
        oldlst.add(price12);

        endlist = prs.mergePrices(oldlst, null);
        Assert.assertEquals(endlist, oldlst);

        System.out.println("Добавление null вместо новых цен протестировано");

    }

    @Test
    public void oldNullTest() {
        ArrayList<Price> newlst = new ArrayList<Price>();
        ArrayList<Price> endlist = new ArrayList<Price>();

        newlst.add(price);
        newlst.add(price1);
        newlst.add(price12);

        endlist = prs.mergePrices(null, newlst);
        Assert.assertEquals(endlist, newlst);

        System.out.println("Добавление нового списка цен при условии, что вместо старого списка null протестировано");

    }

    @Test
    public void oldEmptyTest() {
        ArrayList<Price> oldlst = new ArrayList<Price>();
        ArrayList<Price> newlst = new ArrayList<Price>();
        ArrayList<Price> endlist = new ArrayList<Price>();

        newlst.add(price);
        newlst.add(price1);
        newlst.add(price12);

        endlist = prs.mergePrices(oldlst, newlst);
        Assert.assertEquals(endlist, newlst);

        System.out.println("Добавление новых цен к пустому списку текущих протестировано");

    }

    @Test
    public void bothNullTest() {
        ArrayList<Price> endlist = new ArrayList<Price>();
        ArrayList<Price> checklist = new ArrayList<Price>();

        endlist = prs.mergePrices(null, null);
        Assert.assertEquals(endlist, checklist);

        System.out.println("Добавление null вместо новых и старых цен протестировано");

    }

    @Test
    public void bothEmptyTest() {
        ArrayList<Price> oldlst = new ArrayList<Price>();
        ArrayList<Price> newlst = new ArrayList<Price>();
        ArrayList<Price> checklist = new ArrayList<Price>();
        ArrayList<Price> endlist = new ArrayList<Price>();

        endlist = prs.mergePrices(oldlst, newlst);
        Assert.assertEquals(endlist, checklist);

        System.out.println("Добавление пустых списков вместо новых и старых цен протестировано");

    }

    @Test
    public void newEmptyTest() {


        ArrayList<Price> oldlst = new ArrayList<Price>();
        ArrayList<Price> newlst = new ArrayList<Price>();
        ArrayList<Price> endlist = new ArrayList<Price>();
        oldlst.add(price);
        oldlst.add(price1);
        oldlst.add(price12);


        endlist = prs.mergePrices(oldlst, newlst);
        System.out.println(endlist);
        Assert.assertEquals(endlist, oldlst);

        System.out.println("Добавление пустого списка вместо новых цен протестировано");

    }

    @Test
    public void simpleStressTest() {


        ArrayList<Price> oldlst = new ArrayList<Price>();
        ArrayList<Price> newlst = new ArrayList<Price>();
        ArrayList<Price> endlist = new ArrayList<Price>();
        for (int i = 0; i < 10000; i++) {
            oldlst.add(price);
            newlst.add(price2);
        }


        endlist = prs.mergePrices(oldlst, newlst);

        System.out.println("Стресс-тест между объединением 20000 старых и новых идентичных себе цен окончен");
    }

    @Test
    public void hardStressTest() {


        ArrayList<Price> oldlst = new ArrayList<Price>();
        ArrayList<Price> newlst = new ArrayList<Price>();
        ArrayList<Price> endlist = new ArrayList<Price>();
        Random random = new Random();



        for (int i = 0; i < 3000; i++) {

            Price oldTestprice = new Price((long) 0, "122856", 1, 1, Utils.getStartDate(Month.of(random.nextInt(4 - 1) + 1), random.nextInt(25 - 1) + 1), Utils.getEndDate(Month.of(random.nextInt(10 - 1) + 1), (int) random.nextInt(25 - 1) + 1), (long) 11000);
            Price newTestPrice = new Price((long) 0, "122856", 1, 1, Utils.getStartDate(Month.of(random.nextInt(4 - 1) + 1), random.nextInt(25 - 1) + 1), Utils.getEndDate(Month.of(random.nextInt(10 - 1) + 1), (int) random.nextInt(25 - 1) + 1), (long) 11000);


            oldlst.add(oldTestprice);
            newlst.add(newTestPrice);
        }

        endlist = prs.mergePrices(oldlst, newlst);
        System.out.println("Стресс-тест с 3000 старых цен и 3000 новых цен," +
                " где у каждой цены имеется разброс в несколько месяцев и дней как начала действия цен," +
                " так и их окончания протестирован");
    }


}