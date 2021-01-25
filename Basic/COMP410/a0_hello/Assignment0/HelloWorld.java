package Assignment0;

public class HelloWorld implements HelloWorld_Interface {
	
	public HelloWorld() {

	}
	@Override
	public String say_it() {
		return "hello world";
	}
	@Override
	public String say_it_loud() {
		return "HELLO WORLD";
	}
}
