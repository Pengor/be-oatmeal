
public class CityChainDriver {

	private static final String BETA 	= "0.2";
	private static final String H 		= "1";
	private static final String DELT	= "0.2";
	private static final String GAM		= "0.143";

	//{N, S, E, I, BETA, H, DELT, GAM, V, P_V, M, P_M}
	private static final String[][] DATA = 	{
	/*Lofa 			LBR*/		{"270114"	, "0.998230377", "0.000540513", "0.001229111", BETA, H, DELT, GAM, "", "", "", ""},
	/*Gbarpolu 		LBR*/		{"83758"	, "0.999582129", "0.000226844", "0.000191027", BETA, H, DELT, GAM, "", "", "", ""},
	/*Bong 			LBR*/		{"328919"	, "0.998215366", "0.000456039", "0.001328595", BETA, H, DELT, GAM, "", "", "", ""},
	/*Nimba			LBR*/		{"468088"	, "0.999282186", "0.000469997", "0.000247817", BETA, H, DELT, GAM, "", "", "", ""},
	/*Grand Gedeh 	LBR*/		{"126146"	, "0.999912799", "0.000063419", "0.000023782", BETA, H, DELT, GAM, "", "", "", ""},
	/*River Gee		LBR*/		{"67318"	, "0.999702903", "0.000178258", "0.000118839", BETA, H, DELT, GAM, "", "", "", ""},
	/*Maryland		LBR*/		{"136404"	, "0.999824052", "0.000146623", "0.000029325", BETA, H, DELT, GAM, "", "", "", ""},
	/*Grand Kru		LBR*/		{"57106"	, "0.999369593", "0.000560361", "0.000070045", BETA, H, DELT, GAM, "", "", "", ""},
	/*Sinoe			LBR*/		{"104932"	, "0.999456791", "0.000371669", "0.000171540", BETA, H, DELT, GAM, "", "", "", ""},
	/*River Cess	LBR*/		{"65962"	, "0.999367471", "0.000287513", "0.000345016", BETA, H, DELT, GAM, "", "", "", ""},
	/*Grand Bassa	LBR*/		{"224839"	, "0.999194980", "0.000564849", "0.000240172", BETA, H, DELT, GAM, "", "", "", ""},
	/*Margibi		LBR*/		{"199689"	, "0.993570001", "0.004471954", "0.001958045", BETA, H, DELT, GAM, "", "", "", ""},
	/*Montserrado	LBR*/		{"1144806"	, "0.995859560", "0.002581223", "0.001559216", BETA, H, DELT, GAM, "", "", "", ""},
	/*Bomi			LBR*/		{"82036"	, "0.996221171", "0.002084451", "0.001694378", BETA, H, DELT, GAM, "", "", "", ""},
	/*Grand Cape Mo LBR*/		{"129055"	, "0.997729650", "0.001541978", "0.000728372", BETA, H, DELT, GAM, "", "", "", ""},
	/*Kailahun		SLE*/		{"341690"	, "0.998097691", "0.000248763", "0.001653546", BETA, H, DELT, GAM, "", "", "", ""},
	/*Kenema		SLE*/		{"494139"	, "0.998553039", "0.000431053", "0.001015908", BETA, H, DELT, GAM, "", "", "", ""},
	/*Kono			SLE*/		{"557978"	, "0.999112868", "0.000444462", "0.000442670", BETA, H, DELT, GAM, "", "", "", ""},
	/*Bombali		SLE*/		{"653013"	, "0.998238932", "0.000241955", "0.001519112", BETA, H, DELT, GAM, "", "", "", ""},
	/*Kambia		SLE*/		{"335471"	, "0.999239875", "0.000280203", "0.000479922", BETA, H, DELT, GAM, "", "", "", ""},
	/*Koinadugu		SLE*/		{"434937"	, "0.999505676", "0.000255209", "0.000239115", BETA, H, DELT, GAM, "", "", "", ""},
	/*Port Loko		SLE*/		{"654142"	, "0.997341556", "0.000597730", "0.002060715", BETA, H, DELT, GAM, "", "", "", ""},
	/*Tonkolili		SLE*/		{"168729"	, "0.996657362", "0.000681566", "0.002661072", BETA, H, DELT, GAM, "", "", "", ""},
	/*Bo			SLE*/		{"465048"	, "0.998856032", "0.000468769", "0.000675199", BETA, H, DELT, GAM, "", "", "", ""},
	/*Bonthe		SLE*/		{"325003"	, "0.999938462", "0.000046153", "0.000015384", BETA, H, DELT, GAM, "", "", "", ""},
	/*Moyanba		SLE*/		{"355574"	, "0.999083173", "0.000337482", "0.000579345", BETA, H, DELT, GAM, "", "", "", ""},
	/*Pujehun		SLE*/		{"278119"	, "0.999802243", "0.000086294", "0.000111463", BETA, H, DELT, GAM, "", "", "", ""},
	/*Western Rural SLE*/		{"263619"	, "0.994552745", "0.001179733", "0.004267522", BETA, H, DELT, GAM, "", "", "", ""},
	/*Western Urban SLE*/		{"1040888"	, "0.997434883", "0.000597567", "0.001967551", BETA, H, DELT, GAM, "", "", "", ""},
	/*Boke			GIN*/		{"449405"	, "0.999997775", "0.000000000", "0.000002225", BETA, H, DELT, GAM, "", "", "", ""},
	/*Telimele		GIN*/		{"283639"	, "0.999858976", "0.000000000", "0.000141024", BETA, H, DELT, GAM, "", "", "", ""},
	/*Boffa			GIN*/		{"211063"	, "0.999886290", "0.000000000", "0.000113710", BETA, H, DELT, GAM, "", "", "", ""},
	/*Mali			GIN*/		{"290320"	, "0.999996556", "0.000000000", "0.000003444", BETA, H, DELT, GAM, "", "", "", ""},
	/*Faranah		GIN*/		{"280511"	, "0.999836014", "0.000000000", "0.000163986", BETA, H, DELT, GAM, "", "", "", ""},
	/*Tougue		GIN*/		{"122959"	, "0.999836014", "0.000000000", "0.000016266", BETA, H, DELT, GAM, "", "", "", ""},
	/*Kindia		GIN*/		{"438315"	, "0.999851705", "0.000000000", "0.000148295", BETA, H, DELT, GAM, "", "", "", ""},
	/*Dalaba		GIN*/		{"136320"	, "0.999992665", "0.000000000", "0.000007335", BETA, H, DELT, GAM, "", "", "", ""},
	/*Kouroussa		GIN*/		{"268224"	, "0.999932915", "0.000000000", "0.000067085", BETA, H, DELT, GAM, "", "", "", ""},
	/*Forecariah	GIN*/		{"244649"	, "0.999656651", "0.000000000", "0.000343349", BETA, H, DELT, GAM, "", "", "", ""},
	/*Conakry		GIN*/		{"1667864"	, "0.999763170", "0.000000000", "0.000236830", BETA, H, DELT, GAM, "", "", "", ""},
	/*Coyah			GIN*/		{"264164"	, "0.999356460", "0.000000000", "0.000643540", BETA, H, DELT, GAM, "", "", "", ""},
	/*Dubreka		GIN*/		{"328418"	, "0.999741183", "0.000000000", "0.000258817", BETA, H, DELT, GAM, "", "", "", ""},
	/*Pita			GIN*/		{"277059"	, "0.999974735", "0.000000000", "0.000025265", BETA, H, DELT, GAM, "", "", "", ""},
	/*Dabola		GIN*/		{"182951"	, "0.999956272", "0.000000000", "0.000043728", BETA, H, DELT, GAM, "", "", "", ""},
	/*Siguiri		GIN*/		{"695449"	, "0.999965490", "0.000000000", "0.000034510", BETA, H, DELT, GAM, "", "", "", ""},
	/*Kankan		GIN*/		{"472112"	, "0.999934338", "0.000000000", "0.000065662", BETA, H, DELT, GAM, "", "", "", ""},
	/*Kerouane		GIN*/		{"211017"	, "0.999241767", "0.000000000", "0.000758233", BETA, H, DELT, GAM, "", "", "", ""},
	/*Beyla			GIN*/		{"325482"	, "0.999858671", "0.000000000", "0.000141329", BETA, H, DELT, GAM, "", "", "", ""},
	/*Macenta		GIN*/		{"298282"	, "0.997599587", "0.000000000", "0.002400413", BETA, H, DELT, GAM, "", "", "", ""},
	/*Yomou			GIN*/		{"176664"	, "0.999937735", "0.000000000", "0.000062265", BETA, H, DELT, GAM, "", "", "", ""},
	/*Nzerekore		GIN*/		{"396118"	, "0.999462281", "0.000000000", "0.000537719", BETA, H, DELT, GAM, "", "", "", ""},
	/*Gueckedou		GIN*/		{"291823"	, "0.999078208", "0.000000000", "0.000921792", BETA, H, DELT, GAM, "", "", "", ""},
	/*Lola			GIN*/		{"175213"	, "0.999526291", "0.000000000", "0.000473709", BETA, H, DELT, GAM, "", "", "", ""},
	/*Kissidougo	GIN*/		{"283609"	, "0.999654450", "0.000000000", "0.000345546", BETA, H, DELT, GAM, "", "", "", ""}
	}; 
	
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < DATA.length; i++)
			ChainDriver.main(DATA[i]);
	}

}