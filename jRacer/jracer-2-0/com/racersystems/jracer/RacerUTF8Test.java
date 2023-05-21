package com.racersystems.jracer;

public class RacerUTF8Test {

    public static void main(String[] args) {

	// please adjust this path to match your environment: 

	// String jFamily = "\"C:/jracer-2-0/demo/family-j-utf8.racer\""; 

	String jFamily = "\"/home/mi.wessel/racer-support/jracer-2-0/demo/family-j-utf8.racer\""; 

	String ip = "localhost";
	int port = 8088;

	RacerClient racer = new RacerClient(ip,port);		

	try {

	    racer.openConnection();

	    racer.loggingOn();

	    // UTF-8 Test
	    // (Note: RacerPro must be running in UTF8 mode for this! 
	    // Start as "RacerPro -- -ef @UTF8" 
	    // Thanks to Mr. Kuroda Hisao for providing this japanese version
	    // of the family.racer KB 
	    
	    racer.fullReset();
	    racer.racerReadFile(jFamily); 
	    System.out.println(racer.taxonomy()); 

	    racer.fullReset(); 
	    racer.inKnowledgeBaseM("家族","鈴木家"); 
	    racer.signatureM(":atomic-concepts",
			     "(ヒト 人間 雌 雄 女 男 親 母 父 祖母 伯母 叔父 姉妹 兄弟)",
			     ":roles", 
			     "((子孫を持つ :transitive t) (子供を持つ :parent 子孫を持つ) きょうだいを持つ (姉妹を持つ :parent きょうだいを持つ) (兄弟を持つ :parent きょうだいを持つ) (性を持つ :feature t))",
			     ":individuals",
			     "(みよ はな たろう ゆき とめ)"); 

	    racer.impliesM("*top*","(all 子供を持つ 人間)"); 	    
	    racer.impliesM("(some 子供を持つ *top*)","親");	    
	    racer.impliesM("(some きょうだいを持つ *top*)","(or 姉妹 兄弟)");
	    racer.impliesM("*top*","(all きょうだいを持つ (or 姉妹 兄弟))"); 
	    racer.impliesM("*top*","(all 姉妹を持つ (some 性を持つ 雌))"); 
	    racer.impliesM("*top*","(all 兄弟を持つ (some 性を持つ 雄))"); 
	    racer.impliesM("人間","(and ヒト (some 性を持つ (or 雌 雄)))");
	    racer.disjointM("雌","雄"); 
	    racer.impliesM("女","(and 人間 (some 性を持つ 雌))"); 
	    racer.impliesM("男","(and 人間 (some 性を持つ 雄))"); 
	    racer.equivalentM("親","(and 人間 (some 子供を持つ 人間))"); 
	    racer.equivalentM("母","(and 女 親)"); 
	    racer.equivalentM("父","(and 男 親)"); 
	    racer.equivalentM("祖母","(and 母 (some 子供を持つ (some 子供を持つ 人間)))"); 
	    racer.equivalentM("伯母","(and 女 (some きょうだいを持つ 親))"); 
	    racer.equivalentM("叔父","(and 男 (some きょうだいを持つ 親))"); 
	    racer.equivalentM("兄弟","(and 男 (some きょうだいを持つ 人間))"); 
	    racer.equivalentM("姉妹","(and 女 (some きょうだいを持つ 人間))"); 
	    racer.instanceM("みよ","母"); 
	    racer.relatedM("みよ","はな","子供を持つ"); 
	    racer.relatedM("みよ","たろう","子供を持つ"); 
	    racer.instanceM("はな","母"); 
	    racer.relatedM("はな","ゆき","子供を持つ"); 
	    racer.relatedM("はな","とめ","子供を持つ"); 
	    racer.instanceM("たろう","兄弟"); 
	    racer.relatedM("たろう","はな", "きょうだいを持つ"); 
	    racer.instanceM("たろう","(at-most 1 きょうだいを持つ)"); 
	    racer.relatedM("ゆき","とめ","姉妹を持つ"); 
	    racer.relatedM("とめ","ゆき","姉妹を持つ"); 
	    
	    System.out.println(racer.taxonomy()); 


	} catch (Exception e) {

	    e.printStackTrace();
	    
	}
	
    }
    
}


