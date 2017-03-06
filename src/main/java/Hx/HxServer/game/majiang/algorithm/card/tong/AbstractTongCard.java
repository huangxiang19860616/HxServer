package Hx.HxServer.game.majiang.algorithm.card.tong;


import Hx.HxServer.game.majiang.algorithm.card.IMajiangCard;

/**
 * Created by huangxiang on 2017/2/17 0017.
 */
public abstract class AbstractTongCard implements IMajiangCard {
    private final ECardType cardType = ECardType.TONG;

    private final int maxCount = 4 * 9;
}
