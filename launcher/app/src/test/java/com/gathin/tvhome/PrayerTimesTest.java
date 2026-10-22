package com.gathin.tvhome;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** e-Solat's dates and times, read into what the row under the clock shows. */
public class PrayerTimesTest {

    @Test
    public void readsMalayAndEnglishDates() {
        assertEquals("21-10-2026", PrayerTimes.dateKey("21-Okt-2026"));
        assertEquals("01-08-2026", PrayerTimes.dateKey("01-Ogos-2026"));
        assertEquals("05-03-2026", PrayerTimes.dateKey("05-Mac-2026"));
        assertEquals("31-12-2026", PrayerTimes.dateKey("31-Dec-2026"));
        assertEquals("", PrayerTimes.dateKey("not a date"));
    }

    @Test
    public void readsTimesAndHijri() {
        assertEquals(781, PrayerTimes.minutes("13:01:00"));
        assertEquals(-1, PrayerTimes.minutes("25:00:00"));
        assertEquals("10 Jamadilawal 1448", PrayerTimes.hijri("1448-05-10"));
        assertEquals("1 Ramadan 1448", PrayerTimes.hijri("1448-09-01"));
    }

    @Test
    public void findsTheNextPrayerAndCountsDown() {
        int[] day = {349, 781, 978, 1140, 1210};
        assertEquals(0, PrayerTimes.next(day, 300));
        assertEquals(2, PrayerTimes.next(day, 781));
        assertEquals(-1, PrayerTimes.next(day, 1215));
        assertEquals("dalam 12 min", PrayerTimes.countdown(12));
        assertEquals("dalam 1 j 5 min", PrayerTimes.countdown(65));
        assertEquals("dalam 2 j", PrayerTimes.countdown(120));
        assertEquals("05:49", PrayerTimes.format(349, true));
        assertEquals("1:01", PrayerTimes.format(781, false));
    }

    @Test
    public void formatsLiveCountdownAsHoursMinutesSeconds() {
        assertEquals("00:00:00", PrayerTimes.countdownSeconds(0));
        assertEquals("00:00:01", PrayerTimes.countdownSeconds(1));
        assertEquals("00:01:00", PrayerTimes.countdownSeconds(60));
        assertEquals("01:05:09", PrayerTimes.countdownSeconds(3909));
        assertEquals("24:00:00", PrayerTimes.countdownSeconds(86400));
        assertEquals("00:00:00", PrayerTimes.countdownSeconds(-15));
        assertEquals(1L, PrayerTimes.secondsUntil(1999L, 1000L));
        assertEquals(1L, PrayerTimes.secondsUntil(1001L, 1000L));
        assertEquals(0L, PrayerTimes.secondsUntil(1000L, 1000L));
        assertEquals(0L, PrayerTimes.secondsUntil(999L, 1000L));
    }

    @Test
    public void countdownUsesAbsolutePrayerTimeAndRollsOverMidnight() {
        java.util.TimeZone utc = java.util.TimeZone.getTimeZone("UTC");
        java.util.Calendar now = java.util.Calendar.getInstance(utc, java.util.Locale.ROOT);
        now.clear();
        now.set(2026, java.util.Calendar.OCTOBER, 22, 5, 48, 59);
        PrayerTimes.Day today = new PrayerTimes.Day(new int[]{349, 781, 978, 1140, 1210}, "");
        assertEquals(1000L, PrayerTimes.nextPrayerAtMillis(today, null, now) - now.getTimeInMillis());

        now.set(2026, java.util.Calendar.OCTOBER, 22, 5, 49, 0);
        java.util.Calendar next = java.util.Calendar.getInstance(utc, java.util.Locale.ROOT);
        next.setTimeInMillis(PrayerTimes.nextPrayerAtMillis(today, null, now));
        assertEquals(13, next.get(java.util.Calendar.HOUR_OF_DAY));
        assertEquals(1, next.get(java.util.Calendar.MINUTE));

        now.set(2026, java.util.Calendar.OCTOBER, 22, 23, 59, 59);
        PrayerTimes.Day tomorrow = new PrayerTimes.Day(new int[]{350, 782, 979, 1141, 1211}, "");
        assertEquals(21001000L, PrayerTimes.nextPrayerAtMillis(today, tomorrow, now) - now.getTimeInMillis());
    }
}
