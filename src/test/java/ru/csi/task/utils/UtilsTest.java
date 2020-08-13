package ru.csi.task.utils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.csi.task.model.Price;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.time.Month;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

public class UtilsTest {
    // Устанавливаем кодировку, затем подставляем рефлексией в поле кодировки null, таким образом JVM заново
    // сама проставляет кодировку по умолчанию. Помогло :)
    // Только непонятно с чем это связано...
    @BeforeClass
    public static void setUp() throws NoSuchFieldException, IllegalAccessException {
        System.out.print("");
        System.setProperty("file.encoding", "UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null, null);
        System.out.println("Кодировка установлена");

    }

    Random random = new Random();

    Date date1 = Utils.getStartDate(Month.JANUARY, 1);
    Date date2 = Utils.getEndDate(Month.JANUARY, 31);
    Date date3 = Utils.getStartDate(Month.JANUARY, 10);
    Date date4 = Utils.getEndDate(Month.JANUARY, 20);

    @Test
    public void setTime() {
        Date check = Utils.setTime(date1, 1);
        Date check1 = Utils.setTime(date1, -1);
        Date check2 = Utils.setTime(date2, 5000);
        Date check3 = Utils.setTime(date2, -5000);
        Assert.assertTrue(date1.before(check));
        Assert.assertTrue(date1.after(check1));
        Assert.assertTrue(date2.before(check2));
        Assert.assertTrue(date2.after(check3));
        System.out.println("Установка времени протестирована");
    }

    @Test
    public void isOldContainsNew() {
        boolean first = Utils.isOldContainsNew(date1, date2, date3, date4);
        boolean second = Utils.isOldContainsNew(date3, date4, date1, date2);
        boolean third = Utils.isOldContainsNew(date1, date4, date3, date2);
        boolean fourth = Utils.isOldContainsNew(date4, date2, date1, date3);

        Assert.assertTrue(first);
        Assert.assertTrue(!second);
        Assert.assertTrue(!third);
        Assert.assertTrue(!fourth);

        System.out.println("Новая дата внутри старой протестирована");
    }

    @Test
    public void isNewContainsOld() {
        boolean first = Utils.isNewContainsOld(date1, date2, date3, date4);
        boolean second = Utils.isNewContainsOld(date3, date4, date1, date2);
        boolean third = Utils.isNewContainsOld(date1, date4, date3, date2);
        boolean fourth = Utils.isNewContainsOld(date4, date2, date1, date3);

        Assert.assertTrue(!first);
        Assert.assertTrue(second);
        Assert.assertTrue(!third);
        Assert.assertTrue(!fourth);

        System.out.println("Старая дата внутри новой протестирована");

    }

    @Test
    public void isNewRight() {
        boolean first = Utils.isNewRight(date1, date2, date3, date4);
        boolean second = Utils.isNewRight(date3, date4, date1, date2);
        boolean third = Utils.isNewRight(date1, date4, date3, date2);
        boolean fourth = Utils.isNewRight(date4, date2, date1, date3);

        Assert.assertTrue(!first);
        Assert.assertTrue(!second);
        Assert.assertTrue(third);
        Assert.assertTrue(!fourth);

        System.out.println("Новая дата новее старой протестирована");

    }

    @Test
    public void isNewLeft() {
        boolean first = Utils.isNewLeft(date1, date2, date3, date4);
        boolean second = Utils.isNewLeft(date3, date4, date1, date2);
        boolean third = Utils.isNewLeft(date1, date4, date3, date2);
        boolean fourth = Utils.isNewLeft(date3, date2, date1, date4);
        Assert.assertTrue(!first);
        Assert.assertTrue(!second);
        Assert.assertTrue(!third);
        Assert.assertTrue(fourth);

        System.out.println("Старая дата новее старой протестирована");

    }

    @Test
    public void getStartDate() {
        for (int i = 0; i < 30000; i++) {
            Utils.getStartDate(Month.of(random.nextInt(12 - 1) + 1), random.nextInt(30 - 1) + 1);
        }
        System.out.println("Генерация 30000 объектов Date с начальным временем 00:00:00 протестирована");

    }

    @Test
    public void getEndDate() {
        for (int i = 0; i < 30000; i++) {
            Utils.getEndDate(Month.of(random.nextInt(12 - 1) + 1), random.nextInt(30 - 1) + 1);
        }
        System.out.println("Генерация 30000 объектов Date с начальным временем 23:59:59 протестирована");

    }
}