package Hx.HxServer.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class XAssert {
    private static final Logger s_logger = LoggerFactory.getLogger(XAssert.class);

    private static boolean s_assert_enable = false;

    static {
        assert (s_assert_enable = true); // judge whether assert is enable
    }

    public static class XDumper {
        private final Object m_obj[];

        public XDumper(Object... args) {
            m_obj = args;
        }

        @Override
        public String toString() {
            return TypeUtil.typeToString("--", m_obj);
        }
    }

    public static void checkContinue(boolean condition, Object... params) {
        try {
            check(condition, params);
        } catch (Exception e) {
            afterExceptionContinue(e);
        } catch (Error e) {
            afterErrorContinue(e);
        }
    }

    public static void check(boolean condition, Object... params) {
        if (s_assert_enable) {
            assert condition : new XDumper(params);
        } else {
            if (!condition) {
                throw new AssertionError(new XDumper(params));
            }
        }
    }

    private static void logStack(StackTraceElement[] stackTraceElements, String message, boolean bSleep) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < stackTraceElements.length; i++) {
            s_logger.error("{}", stackTraceElements[i]);
            stringBuilder.append(stackTraceElements[i] + "\n");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            FileOutputStream outputStream = new FileOutputStream("log/dump_game.txt", true);

            outputStream.write("\n###################################################\n".getBytes());
            outputStream.write(("assert enable:" + s_assert_enable + "\n").getBytes());
            outputStream.write(sdf.format(Calendar.getInstance().getTime()).getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.write("\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n".getBytes());
            outputStream.write(TypeUtil.typeToString("", message).getBytes());
            outputStream.write("---------------------------------------------------".getBytes());

            outputStream.close();

            if (bSleep) {
                Thread.sleep(1000);
            }
        } catch (Exception exception) {

        } catch (Error e) {

        }
    }

    private static void logStackForRole(StackTraceElement[] stackTraceElements, String message, Integer roleId, boolean bSleep) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < stackTraceElements.length; i++) {
            s_logger.error("{}", stackTraceElements[i]);
            stringBuilder.append(stackTraceElements[i] + "\n");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            FileOutputStream outputStream = new FileOutputStream("log/dump_game.txt", true);

            outputStream.write("\n###################################################\n".getBytes());
            outputStream.write(("assert enable:" + s_assert_enable + "\n").getBytes());
            outputStream.write(sdf.format(Calendar.getInstance().getTime()).getBytes());
            outputStream.write(("\nroleid:" + roleId + "\n").getBytes());
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.write("\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n".getBytes());
            outputStream.write(TypeUtil.typeToString("", message).getBytes());
            outputStream.write("---------------------------------------------------".getBytes());

            outputStream.close();

            if (bSleep) {
                Thread.sleep(1000);
            }
        } catch (Exception exception) {

        } catch (Error e) {

        }
    }

    public static void afterErrorContinue(Error e, Integer roleid) {
        e.printStackTrace();
        logStackForRole(e.getStackTrace(), e.getMessage(), roleid, false);
    }

    public static void afterExceptionContinue(Exception e, Integer roleid) {
        e.printStackTrace();
        logStackForRole(e.getStackTrace(), e.getMessage(), roleid, false);
    }

    public static void afterErrorContinue(Error e, String url) {
        e.printStackTrace();
        logStackForUrl(e.getStackTrace(), e.getMessage(), url, false);
    }

    public static void afterExceptionContinue(Exception e, String url) {
        e.printStackTrace();
        logStackForUrl(e.getStackTrace(), e.getMessage(), url, false);
    }

    private static void logStackForUrl(StackTraceElement[] stackTraceElements, String message, String url, boolean bSleep) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < stackTraceElements.length; i++) {
            s_logger.error("{}", stackTraceElements[i]);
            stringBuilder.append(stackTraceElements[i] + "\n");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            FileOutputStream outputStream = new FileOutputStream("log/dump_game.txt", true);

            outputStream.write("\n###################################################\n".getBytes());
            outputStream.write(("assert enable:" + s_assert_enable + "\n").getBytes());
            outputStream.write(sdf.format(Calendar.getInstance().getTime()).getBytes());
            outputStream.write(("\nurl:" + url + "\n").getBytes());
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.write("\n+++++++++++++++++++++++++++++++++++++++++++++++++++\n".getBytes());
            outputStream.write(TypeUtil.typeToString("", message).getBytes());
            outputStream.write("---------------------------------------------------".getBytes());

            outputStream.close();

            if (bSleep) {
                Thread.sleep(1000);
            }
        } catch (Exception exception) {

        } catch (Error e) {

        }
    }

    public static void afterErrorContinue(Error e) {
        e.printStackTrace();
        logStack(e.getStackTrace(), e.getMessage(), false);
    }

    public static void afterExceptionContinue(Exception e) {
        e.printStackTrace();
        logStack(e.getStackTrace(), e.getMessage(), false);
    }

    public static void afterError(Error e) {
        e.printStackTrace();

        logStack(e.getStackTrace(), e.getMessage(), true);

        System.exit(-1);
    }

    public static void afterException(Exception e) {
        e.printStackTrace();

        logStack(e.getStackTrace(), e.getMessage(), true);

        System.exit(-1);
    }
}
