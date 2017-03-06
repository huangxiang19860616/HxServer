package Hx.HxServer.game.majiang.algorithm;


import Hx.HxServer.game.majiang.algorithm.card.IMajiangCard;
import Hx.HxServer.game.majiang.algorithm.card.tong.*;
import Hx.HxServer.game.majiang.algorithm.card.wan.*;
import Hx.HxServer.game.majiang.algorithm.card.tiao.*;

/**
 * Created by huangxiang on 2017/2/17 0017.
 */
public interface IMajiangJoy {

    IMajiangCard maJiangCard[] = {
            new OneWan(), new OneWan(), new OneWan(), new OneWan(),
            new TwoWan(), new TwoWan(), new TwoWan(), new TwoWan(),
            new ThreeWan(), new ThreeWan(), new ThreeWan(), new ThreeWan(),
            new FourWan(), new FourWan(), new FourWan(), new FourWan(),
            new FiveWan(), new FiveWan(), new FiveWan(), new FiveWan(),
            new SixWan(), new SixWan(), new SixWan(), new SixWan(),
            new SevenWan(), new SevenWan(), new SevenWan(), new SevenWan(),
            new EightWan(), new EightWan(), new EightWan(), new EightWan(),
            new NineWan(), new NineWan(), new NineWan(), new NineWan(),

            new OneTong(), new OneTong(), new OneTong(), new OneTong(),
            new TwoTong(), new TwoTong(), new TwoTong(), new TwoTong(),
            new ThreeTong(), new ThreeTong(), new ThreeTong(), new ThreeTong(),
            new FourTong(), new FourTong(), new FourTong(), new FourTong(),
            new FiveTong(), new FiveTong(), new FiveTong(), new FiveTong(),
            new SixTong(), new SixTong(), new SixTong(), new SixTong(),
            new SevenTong(), new SevenTong(), new SevenTong(), new SevenTong(),
            new EightTong(), new EightTong(), new EightTong(), new EightTong(),
            new NineTong(), new NineTong(), new NineTong(), new NineTong(),

            new OneTiao(), new OneTiao(), new OneTiao(), new OneTiao(),
            new TwoTiao(), new TwoTiao(), new TwoTiao(), new TwoTiao(),
            new ThreeTiao(), new ThreeTiao(), new ThreeTiao(), new ThreeTiao(),
            new FourTiao(), new FourTiao(), new FourTiao(), new FourTiao(),
            new FiveTiao(), new FiveTiao(), new FiveTiao(), new FiveTiao(),
            new SixTiao(), new SixTiao(), new SixTiao(), new SixTiao(),
            new SevenTiao(), new SevenTiao(), new SevenTiao(), new SevenTiao(),
            new EightTiao(), new EightTiao(), new EightTiao(), new EightTiao(),
            new NineTiao(), new NineTiao(), new NineTiao(), new NineTiao(),
    };

    enum EJoyState{PreStart, Start, InGame, Close}

    void init();

    void start();

    void stop();

    void dispatchInputMessage();

    void sendOutputMessage();

    void reset();
}
