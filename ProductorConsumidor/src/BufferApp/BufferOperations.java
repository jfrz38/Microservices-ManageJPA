package BufferApp;


/**
* BufferApp/BufferOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Buffer.idl
* martes 29 de noviembre de 2016 10H32' CET
*/

public interface BufferOperations 
{
  int num_elementos ();
  boolean put (String elemento);
  boolean get (org.omg.CORBA.StringHolder elemento);
  boolean read (org.omg.CORBA.StringHolder elemento);
  void shutdown ();
} // interface BufferOperations
