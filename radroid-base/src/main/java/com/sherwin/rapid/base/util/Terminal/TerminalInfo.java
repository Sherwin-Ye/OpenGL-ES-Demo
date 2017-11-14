package com.sherwin.rapid.base.util.Terminal;

/**
 * @author Sherwin.Ye
 * @data 2015-11-27 上午9:06:53
 * @desc TerminalInfo.java
 */
public class TerminalInfo {

	private String hsman;// 厂商
	private String hstype;// 机型
	private Short screenWidth;// 屏幕宽
	private Short screenHeight;// 屏幕高
	private Short screenDPI;// 屏幕DPI
	private Float density;// 屏幕像素比例
	private Integer sizeRAM;// ram大小
	private Integer sizeROM;// rom大小
	private Integer sizeSD;// SD卡大小
	private String cpu;// cpu信息
	private String versionOS;// 系统版本
	private Boolean root;// 是否root
	private String language;// 系统语言
	private String hardware;// 硬件信息
	private Long buildTime;// 系统编译时间
	private String mac;// mac地址
	private String imei;
	private String phoneUuid; // imei 的替代策略

	private String imsi;
	private Short phoneType;// 手机制式类型
	private Short networkType;// 网络类型
	private String networkTypeName;// 网络类型名称
	private Short networkTypeSub;// 网络子类型
	private String networkTypeSubName;// 网络子类型名称
	private Integer cellId;// Cell Identity，基站编号
	private Integer lac;// Location Area Code，位置区域码
	private String longitude;// 经度
	private String latitude;// 纬度

	public String getHsman() {
		return hsman;
	}

	public void setHsman(String hsman) {
		this.hsman = hsman;
	}

	public String getHstype() {
		return hstype;
	}

	public void setHstype(String hstype) {
		this.hstype = hstype;
	}

	public Short getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(Short screenWidth) {
		this.screenWidth = screenWidth;
	}

	public Short getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(Short screenHeight) {
		this.screenHeight = screenHeight;
	}

	public Short getScreenDPI() {
		return screenDPI;
	}

	public void setScreenDPI(Short screenDPI) {
		this.screenDPI = screenDPI;
	}

	public Float getDensity() {
		return density;
	}

	public void setDensity(Float density) {
		this.density = density;
	}

	public Integer getSizeRAM() {
		return sizeRAM;
	}

	public void setSizeRAM(Integer sizeRAM) {
		this.sizeRAM = sizeRAM;
	}

	public Integer getSizeROM() {
		return sizeROM;
	}

	public void setSizeROM(Integer sizeROM) {
		this.sizeROM = sizeROM;
	}

	public Integer getSizeSD() {
		return sizeSD;
	}

	public void setSizeSD(Integer sizeSD) {
		this.sizeSD = sizeSD;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getVersionOS() {
		return versionOS;
	}

	public void setVersionOS(String versionOS) {
		this.versionOS = versionOS;
	}

	public Boolean getRoot() {
		return root;
	}

	public void setRoot(Boolean root) {
		this.root = root;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHardware() {
		return hardware;
	}

	public void setHardware(String hardware) {
		this.hardware = hardware;
	}

	public Long getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(Long buildTime) {
		this.buildTime = buildTime;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public String getPhoneUuid() {
		return phoneUuid;
	}

	public void setPhoneUuid(String phoneUuid) {
		this.phoneUuid = phoneUuid;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Short getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(Short phoneType) {
		this.phoneType = phoneType;
	}

	public Short getNetworkType() {
		return networkType;
	}

	public void setNetworkType(Short networkType) {
		this.networkType = networkType;
	}

	public String getNetworkTypeName() {
		return networkTypeName;
	}

	public void setNetworkTypeName(String networkTypeName) {
		this.networkTypeName = networkTypeName;
	}

	public Short getNetworkTypeSub() {
		return networkTypeSub;
	}

	public void setNetworkTypeSub(Short networkTypeSub) {
		this.networkTypeSub = networkTypeSub;
	}

	public String getNetworkTypeSubName() {
		return networkTypeSubName;
	}

	public void setNetworkTypeSubName(String networkTypeSubName) {
		this.networkTypeSubName = networkTypeSubName;
	}

	public Integer getCellId() {
		return cellId;
	}

	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}

	public Integer getLac() {
		return lac;
	}

	public void setLac(Integer lac) {
		this.lac = lac;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
