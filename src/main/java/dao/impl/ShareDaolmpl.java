package dao.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import dao.BaseDao;
import dao.ShareDao;
import entity.ShareMessage;
import dao.RSProcessor;

/**
 * @author: 林源
 * @className: doprefix
 * @packageName: biz
 * @lastmodifiedtime:2019年9月1日 
 **/
public  class ShareDaolmpl extends BaseDao implements ShareDao {

	@Override
	public int countallshare() {
		String sql = "select count(*) as a sharecount from sharelist";

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				int count = 0;
				if (rs != null) {
					if (rs.next()) {
						count = rs.getInt("sharecount");
					}
				}
				return new Integer(count);
			}

		};

		return (Integer) this.executeQuery(getResultProcessor, sql);



	}


	@Override
	public int countusershare(String user) {

		String sql = "select count(*) as a sharecount from sharelist where user = ?";
		Object[] params = { user };
		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				int count = 0;
				if (rs != null) {
					if (rs.next()) {
						count = rs.getInt("sharecount");
					}
				}
				return new Integer(count);
			}

		};

		return (Integer) this.executeQuery(getResultProcessor, sql,params);


	}

	@Override
	public int addshare(ShareMessage share) {
		String sql = "insert sharelist (user,filename,uuidName,url,shareTime,downloads,size,type) values(?,?,?,?,?,?,?,?)";

		Object[] params = { share.getUser(),share.getFileName(),share.getUuidName(),share.getUrl(),
				share.getShareTime(),share.getDownloads(),share.getSize(),share.getType()};
		return this.executeUpdate(sql, params);

	}

	@Override
	public int delshare(String url) {

		String sql = "delete from sharelist where url=?";
		Object[] params = { url };
		return this.executeUpdate(sql, params);

		// TODO Auto-generated method stub
	}

	@Override
	public int delallshare(String user) {

		String sql = "delete from sharelist where user = ?";
		Object[] params = { user };
		return this.executeUpdate(sql, params);
	}

	@Override
	public Vector<ShareMessage> findbyuser(String user) {
		String sql = "select * from sharelist where  user = ?";
		Object[] params = { user };

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				Vector<ShareMessage> share = new Vector<ShareMessage>();

				while (rs.next()) {
					String user = rs.getString("user");
					String fileName = rs.getString("fileName");
					String uuidName = rs.getString("uuidName");
					String url = rs.getString("url");
					String shareTime = rs.getString("shareTime");

					int downloads= rs.getInt("downloads");
					String size = rs.getString("size");
					String type = rs.getString("type");
					ShareMessage ashare = new ShareMessage(user,fileName,uuidName,url, shareTime,  downloads,size, type);
					share.add(ashare);
				}
				return share;

			}
		};

		return (Vector<ShareMessage>) this.executeQuery(getResultProcessor, sql, params);
	}


	@Override
	public String adddownload(String url) {
		// TODO Auto-generated method stub
		String sql = "set downloads=(downloads+1) from sharelist where  url = ?";
		Object[] params = { url };

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				String akey="";
				if (rs.next()) {
					String key = rs.getString("key");

					akey=key;
				}
				return akey;
			}
		};

		return (String) this.executeQuery(getResultProcessor, sql, params);



	}


	@Override
	public Vector<ShareMessage> findbyurl(String url) {
		String sql = "select * from sharelist where  url = ?";
		Object[] params = { url };

		RSProcessor getResultProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				Vector<ShareMessage> share = new Vector<ShareMessage>();

				while (rs.next()) {
					String user = rs.getString("user");
					String fileName = rs.getString("fileName");
					String uuidName = rs.getString("uuidName");
					String url = rs.getString("url");
					String shareTime = rs.getString("shareTime");

					int downloads= rs.getInt("downloads");
					String size = rs.getString("size");
					String type = rs.getString("type");
					ShareMessage ashare = new ShareMessage(user,fileName,uuidName,url, shareTime,  downloads,size, type);
					share.add(ashare);
				}
				return share;

			}
		};

		return (Vector<ShareMessage>) this.executeQuery(getResultProcessor, sql, params);
	}

















}
