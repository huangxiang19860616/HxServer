package Hx.HxServer.game.majiang.algorithm.card.wan;


import Hx.HxServer.game.majiang.algorithm.card.IMajiangCard;

/**
 * Created by huangxiang on 2017/2/17 0017.
 */
public abstract class AbstractWanCard implements IMajiangCard {

    private final IMajiangCard.ECardType cardType = ECardType.WAN;

    private final int maxCount = 4 * 9;
}
