package uicc.test.util;

public class UICCTestConstants {
    //MF
    public final static short FID_MF       = (short)0x3F00;

    //  Reserved IDs
    //  These IDs are used to test that a DF/EF does not exist
    //  and are thus reserved. They shall not be used for perso.
    public final static short FID_EF_RFU0 = (short)0x6F29;
    public final static short FID_EF_RFU1 = (short)0x6F2A;
    public final static short FID_EF_RFU2 = (short)0x6F2B;

    public final static short FID_DF_RFU1 = (short)0x5F01;
    public final static short FID_DF_RFU2 = (short)0x5F02;
    public final static short FID_DF_RFU3 = (short)0x5F03;
    
    // DF Ids
    public final static short FID_DF_TEST      = (short)0x7F4A;
    public final static short FID_DF_SUB_TEST  = (short)0x5F10;

    public final static short FID_DF_ADF2      = (short)0x7F4B;

    // File IDs
    public final static short FID_EF_TNR  = (short)0x6F01;
    public final static short FID_EF_TNU  = (short)0x6F02;
    public final static short FID_EF_TARU = (short)0x6F03;

    public final static short FID_EF_CNR  = (short)0x6F04;
    public final static short FID_EF_CNU  = (short)0x6F05;
    public final static short FID_EF_CNIC = (short)0x6F1C;
    public final static short FID_EF_CNDE = (short)0x6F07;
    public final static short FID_EF_CNAC = (short)0x6F08;
    public final static short FID_EF_CARU = (short)0x6F09;

    public final static short FID_EF_LNR  = (short)0x6F0A;
    public final static short FID_EF_LNU  = (short)0x6F0B;
    public final static short FID_EF_LARU = (short)0x6F0C;

    public final static short FID_EF_CINA = (short)0x6F0D;

    public final static short FID_EF_TRAC = (short)0x6F0E;
    public final static short FID_EF_TDAC = (short)0x6F0F;

    public final static short FID_EF_CIAC = (short)0x6F10;
    public final static short FID_EF_CIAA = (short)0x6F11;
    public final static short FID_EF_CNRA = (short)0x6F12;
    public final static short FID_EF_CUAC = (short)0x6F13;

    public final static short FID_EF_TAAC = (short)0x6F14;

    public final static short FID_EF_LADA = (short)0x6F15;

    public final static short FID_EF_TAAA = (short)0x6F16;

    public final static short FID_EF_LRUA = (short)0x6F17;
    public final static short FID_EF_LUPC = (short)0x6F18;
    
    public final static short FID_EF_NOSH = (short)0x6F19;
    
    public final static short FID_EF_LSEA = (short)0x6F1A;
    public final static short FID_EF_CSEA = (short)0x6F1B;

    public final static short FID_EF_TERM = (short)0x6F30;
    public final static short FID_DF_TERM = (short)0x5F30;
    
    public final static short FID_EF_TAA  = (short)0x4F10;

    public final static short FID_EF_LARR1 = (short)0x6FA1;
    public final static short FID_EF_LARR2 = (short)0x6FA2;
    public final static short FID_EF_LARR3 = (short)0x6FA3;
    public final static short FID_EF_LARR4 = (short)0x6FA4;
    public final static short FID_EF_LARR5 = (short)0x6FA5;

    public final static short FID_EF_TARR1 = (short)0x6FB1;
    public final static short FID_EF_TARR2 = (short)0x6FB2;
    public final static short FID_EF_TARR3 = (short)0x6FB3;
    public final static short FID_EF_TARR4 = (short)0x6FB4;
    public final static short FID_EF_TARR5 = (short)0x6FB5;
    
    public final static short FID_EF_CARR1 = (short)0x6FC1;
    public final static short FID_EF_CARR2 = (short)0x6FC2;
    public final static short FID_EF_CARR3 = (short)0x6FC3;
    public final static short FID_EF_CARR4 = (short)0x6FC4;
    public final static short FID_EF_CARR5 = (short)0x6FC5;
    
    public final static short FID_DF_ARR1  = (short)0x5F11;
    public final static short FID_EF_TAR1T = (short)0x4F11;

    public final static short FID_DF_ARR2  = (short)0x5F12;
    public final static short FID_EF_TAR2T = (short)0x4F12;

    public final static short FID_DF_ARR3  = (short)0x5F13;
    public final static short FID_EF_TAR3T = (short)0x4F13;

    public final static short FID_DF_ARR4  = (short)0x5F14;
    public final static short FID_EF_TAR4T = (short)0x4F14;

    public final static short FID_DF_ARR5  = (short)0x5F15;
    public final static short FID_EF_TAR5T = (short)0x4F15;

    public final static short FID_EF_ARR_MF   = (short)0x2F06;
    public final static short FID_EF_ARR_ADF  = (short)0x6F06;

    public final static short FID_EF_UICC  = (short)0x2FE4;
    
    public final static short FID_ADF      = (short)0x7FFF;

    public final static short FID_EF_NOSH2 = (short)0x6FC6;
    public final static short FID_EF_LTERM = (short)0x6FC7;
    public final static short FID_EF_CTERM = (short)0x6FC8;

    public final byte[] AID_ADF1 = {(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00,
                                    (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF,
                                    (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89,
                                    (byte)0xE0, (byte)0x00, (byte)0x00, (byte)0x02};

    public final byte[] AID_ADF2 = {(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00,
                                    (byte)0x09, (byte)0x00, (byte)0x05, (byte)0xFF,
                                    (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x89,
                                    (byte)0xD0, (byte)0x00, (byte)0x00, (byte)0x02};
}
