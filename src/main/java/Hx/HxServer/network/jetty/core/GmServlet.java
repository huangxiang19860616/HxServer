package Hx.HxServer.network.jetty.core;

import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;

import Hx.HxServer.network.jetty.core.FsGmMsgRegistry;
import Hx.HxServer.network.jetty.core.GmAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

// resp 不能异步发送 TODO 解决下
public class GmServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            InputStream readStream = req.getInputStream();
            int buffSize = req.getContentLength();
            if (buffSize <= 0) {
                return;
            }
            byte[] buffer = new byte[buffSize];
            int leftSize = buffSize;
            while (leftSize > 0) {
                leftSize -= readStream.read(buffer, buffSize - leftSize, leftSize);
            }
            String msgIdStr = req.getHeader("msgid");
            if (msgIdStr == null) {
                return;
            }

            int msgId = Integer.parseInt(msgIdStr);
            Parser<?> parser = FsGmMsgRegistry.REGISTRY.getParser(msgId);

            Object msg = parser.parseFrom(buffer);

            GmAction action = FsGmMsgRegistry.REGISTRY.getAction(msgId);
            Object respMsg = null;
            try {
                Object rawAction = action; // 跳过Java泛型，以及编译器警告！
                respMsg = ((GmAction) rawAction).execute(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (respMsg != null) {
                DataOutputStream out = new DataOutputStream(resp.getOutputStream());
                out.write(((MessageLite)respMsg).toByteArray());
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
