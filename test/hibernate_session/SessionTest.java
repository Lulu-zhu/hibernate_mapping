package hibernate_session;

import org.hibernate.Session;
import org.hibernate.Transaction;

import junit.framework.TestCase;

public class SessionTest extends TestCase {

	public void testHello1(){
		
		System.out.println("----SessionTest.Hello1(----");
//		this.assertEquals("hello", "hesllo");
	}
	
	public void testSave1(){
		Session session = null;
		Transaction tx = null;
//		User user = null;
		try{
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();
			
			Group group = new Group();
			group.setName("1");
			
//			session.save(group);
			
			User user1 = new User();
			user1.setName("ss");
			user1.setGroup(group);
			
			User user2 = new User();
			user2.setName("试试");
			user2.setGroup(group);
			
//			不能成功保存，抛出异常。因为group为transient状态，oid没有分配值
//			persistent状态的对象不能引用transient状态的对象
			
//			可以正确存储
//			user主键是uuid，完成save后提交给session
//			没有发出insert，此时id已经生成，session中的exitinsession为false
			session.save(user1);
			session.save(user2);
			
			
//			主键生成策略是native，此时save后将执行insert操作，返回有数据库的id
//			隔离级别设为可重复读，可以看到
			
//			session。flush();
//			调用缓存，hibernate会清理缓存，执行sql
//			如果数据库的隔离级别设置为未提交读，那么可以看到flush过的数据
//			采用了cascade属性，会先保存group
//			并且session中的exitindatabas为true

//			flush后hibernate会清理缓存，将user入库，执行sql，将user对象保存到数据库中
//			将session中的user对象清除，并且设置中session中exitsindatabase为true

			
			
			//			session。evict();清理缓存
//			将user对象从session中逐出，session中的entityentries
//			无法成功提交，hibernate在清理缓存是，在session的insertion的集合中取出user进行insert
//			更新entityentries中的exitsindatabase为true，evict已经将user从session中的entity中逐出了
//			找不到相关数据，无法更新

			
			//			提交事务，默认情况下，commit操作会先执行flush清理缓存
			tx.commit();//可以成功提交，因为hibernate在清理缓存中，在insertion临时集合中找数据
//			找不到，没有发出sql语句，没有更新状态exitsindatabase，不报错
			
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			
		}finally{
			
			HibernateUtils.closeSession(session);
		}
		
//		detached״̬
//		user.setName("zhangsan");
		try{
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			User user = (User)session.load(User.class,3);
			
			System.out.println(user.getName());
			
			
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			HibernateUtils.closeSession(session);
		}
	}
	
}
