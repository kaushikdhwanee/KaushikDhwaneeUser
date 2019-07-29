package in.tiqs.kaushikdhwaneeuser.data_base;

public class User {
	String name="",mobile="",email="",city="",uid="",id="",otp = "";
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public User()
	{
		
	}
	public User(String name, String mobile, String email, String city)
	{
		this.name=name;
		this.mobile=mobile;
		this.email=email;
		this.city=city;
	}
	public User(String id, String uid, String mobile)
	{
		this.uid=uid;
		this.mobile=mobile;
		this.id=id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	

}
