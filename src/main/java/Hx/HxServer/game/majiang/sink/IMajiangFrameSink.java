package Hx.HxServer.game.majiang.sink;


import Hx.HxServer.game.majiang.algorithm.IMajiangJoy;

/**
 * Created by huangxiang on 2017/2/16 0016.
 *
 * ��������
 */
public interface IMajiangFrameSink {
    enum  ESinkType
    {
        JingBiSink, SiRenSink
    }

    enum EJoyType
    {
        GuiYangZhuoJi
    }

    //��ʼ�����ӣ���������
    void initSink(IMajiangJoy joy);

    //��������
    void setPassword(String pwd);

    //��λ����
    void resetSink();

    //��ʼ��Ϸ
    void startGame();

    //������Ϸ
    void stopGame();

    //�����ⲿ��Ϣ
    void dispatchInputMessage();

    //�Ƴ��ڲ���Ϣ
    void sendOutputMessage();
}
