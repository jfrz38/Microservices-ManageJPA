package ClienteTest;

import BufferApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class Cliente {
	static Buffer bufferImpl;

	public static void main(String args[]) {
		try {
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = "Buffer";
			bufferImpl = BufferHelper.narrow(ncRef.resolve_str(name));
			//StringHolder elem = new StringHolder();
			bufferImpl.shutdown();
		} catch (Exception e) {
			System.err.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
}
