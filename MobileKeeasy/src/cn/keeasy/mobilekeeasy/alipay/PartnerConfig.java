package cn.keeasy.mobilekeeasy.alipay;

public class PartnerConfig {

	// 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	public static final String PARTNER = "2088901164948693";
	// 商户收款的支付宝账号
	public static final String SELLER = "keeasy@yeah.net";
	// 商户（RSA）私钥
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMDvs2yIa5In89hDqQPSK9k77StKV31zsxMzi7sM4XdqLdYX8rd45I0NUnnSP8Bv2XCht4ul7VUgkNTODfWUtw1O6d6hRdFkwnhaX6cYAxHKjMdDISwANxI8koFCBu3pbyJ8K98SGsOt/bFF3hf7Vo5kfwPCNGaE5Mlq2pA1yBm5AgMBAAECgYEAjS3ZiU26CVUa5eF8x8L/OxLS1Z6srqv/GcnaMaGXSp5zs/oKLZ9U/rIE7AehhttRrU1o1dMw+928nTu6kz2jQ9/4+uUU/bM8r0rqJl1ePuq3EGiQc1XJ3dyU1IrPHNtgRHnjUqcCjm91PPEcKf/tpyN62mS8bu2ScJFNHVEwmkECQQDrLnpnRBm6UQExygLc0GYM6bf4Dbiqcqdz5FTemWaJt0BzlPaqu5ufSHKdj0Cd3exf7wBHGlIkVXcRJcYoPWdNAkEA0gPkA0Iqwbn832EK4JKsCNpIdPy10OcueGEKaveerZCjGFPgz6GpvoeRmdidbyc9lsxIaQWwekLuUDzmayn+HQJATG20Va7p9YSxsK6Hs57+KyAhKzm6U5ojFSSU+Co+Cm9FQMqeRunlLyRqKw4M38DpOlv/aRwCqdAke6wc86LQ2QJABF74W+kAos+QJ8YJR+tFkVmZsHWiAxsMqIy8fsVwlAQyoMr+HtQqiECiEyWjATE74uuVhjoMFj9WXm0mDoztMQJALeBAnXyWcAvEL0tEQPSt3mutQs0Qpc7t6UAB5biS5Iq+2vGop3GhYMDXcbIyRb45pGrlRvgvO0FI2KMbnZVN5Q==";
	// 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
	public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4hKHIywpPh64slls6aAnjh5VcL49zkPnPoyWt 9YiwR0yxlx4pvZAiuQdJ94CQpMIo13qIG2Rspwl4r7Hco9vwDKr0xLb91FjxtVvq3FcI/Orxhmeo fVW4tYwf9GvhCv8m3p7GXQkbNWWKeYlh1WgUOaV5Kr2+qP8Io3Igj4efIwIDAQAB";
	// 支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致
	public static final String ALIPAY_PLUGIN_NAME = "alipay_msp.apk";

}
