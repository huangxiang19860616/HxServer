package Hx.HxServer;

import Hx.HxServer.core.extension.impl.ServiceExtension;
import Hx.HxServer.core.service.IService;
import Hx.HxServer.service.impl.*;

/**
 * Created by huangxiang on 2017/2/28 0028.
 * 初始化各种需要服务，
 */
public class GameExtension extends ServiceExtension {

    private IService configService = null; //配置服务
    private IService chatService = null;   //聊天服务
    private IService loginService = null;  //登陆服务
    private IService dbService = null;     //db服务
    private IService gameService = null;   //玩法服务
    private IService playerService = null; //玩家服务，用户数据

    public void init()
    {
        super.init();

        configService = new ConfigServiceImpl("ConfigService");
        chatService = new ChartServiceImpl("ChartService");
        loginService = new LoginServiceImpl("LoginService");
        dbService = new DbServiceImpl("DbService");
        gameService = new GameServiceImpl("GameService");
        playerService = new PlayerServiceImpl("PlayerService");

        registService(configService);
        registService(chatService);
        registService(loginService);
        registService(dbService);
        registService(gameService);
        registService(playerService);
    }

    public void destory()
    {
        unRegistService(loginService);
        unRegistService(gameService);
        unRegistService(chatService);
        unRegistService(playerService);
        unRegistService(dbService);
        unRegistService(configService);

        super.destory();
    }
}
