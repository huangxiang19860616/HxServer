package Hx.HxServer.game.majiang.algorithm.card.tiao;


import Hx.HxServer.game.majiang.algorithm.card.IMajiangCard;

/**
 * Created by huangxiang on 2017/2/17 0017.
 */
public abstract class AbstractTiaoCard implements IMajiangCard {
    private final ECardType cardType = ECardType.TIAO;

    private final int maxCount = 4 * 9;
}
