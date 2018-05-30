package org.peanut.common.utils;

import javax.net.ssl.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;

/**
 * @author peanut
 * @date   2018/05/16
 *
 * ios 支付验证类
 */
public class IOSVerify
{
	private static class TrustAnyTrustManager implements X509TrustManager
	{

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException
		{
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException
		{
		}

		public X509Certificate[] getAcceptedIssuers()
		{
			return new X509Certificate[]
			{};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier
	{
		public boolean verify(String hostname, SSLSession session)
		{
			return true;
		}
	}

	private static final String url_sandbox = "https://sandbox.itunes.apple.com/verifyReceipt";
	private static final String url_verify = "https://buy.itunes.apple.com/verifyReceipt";

	/**
	 * 苹果服务器验证
	 * 
	 * @param receipt
	 *            账单
     * @param verifyState 验证方式，如沙河传入sandbox,无也传""或null
	 * @return null 或返回结果 沙盒 https://sandbox.itunes.apple.com/verifyReceipt
	 *
	 */
	public static String buyAppVerify(String receipt, String verifyState)
	{
		String url = url_verify;
		if (verifyState != null && "sandbox".equals(verifyState))
		{
			url = url_sandbox;
		}
		// String buyCode=getBASE64(receipt);
		String buyCode = receipt;
		try
		{
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[]
			{ new TrustAnyTrustManager() }, new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console
					.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "text/json");
			conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			BufferedOutputStream hurlBufOus = new BufferedOutputStream(
					conn.getOutputStream());

			String str = String.format(Locale.CHINA, "{\"receipt-data\":\""
					+ buyCode + "\"}");
			hurlBufOus.write(str.getBytes());
			hurlBufOus.flush();

			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}

			return sb.toString();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

}