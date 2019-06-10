import com.my.schoolshop.dto.ShopExecution;
import com.my.schoolshop.model.Area;
import com.my.schoolshop.model.Shop;
import com.my.schoolshop.model.ShopCategory;
import com.my.schoolshop.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.reflect.generics.tree.VoidDescriptor;

import javax.xml.ws.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest{
    @Autowired
    private ShopService shopService;


    /*@Test
	public void testAddShop() throws Exception {
		Shop shop = new Shop();
		Area area = new Area();
		ShopCategory sc = new ShopCategory();

		shop.setOwnerId(8L);
		area.setAreaId(1L);
		sc.setShopCategoryId(1L);
		shop.setShopName("mytest1");
		shop.setShopDesc("mytest1");
		shop.setShopAddr("testaddr1");
		shop.setPhone("13810524526");
		shop.setShopImg("test1");
//		shop.setLongitude(1D);
//		shop.setLatitude(1D);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("审核中");
		shop.setArea(area);
		shop.setShopCategory(sc);
		ShopExecution se = shopService.addShop(shop);
		assertEquals("mytest1", se.getShop().getShopName());
	}
*/



   /* @Test
    public void testAddShop1() throws Exception {
        Shop shop = new Shop();
        Area area = new Area();
        ShopCategory sc = new ShopCategory();

        shop.setOwnerId(8L);
        area.setAreaId(3L);
        sc.setShopCategoryId(24L);
        shop.setShopName("mytest1");
        shop.setShopDesc("mytest1");
        shop.setShopAddr("testaddr1");
        shop.setPhone("13810524526");
//		shop.setLongitude(1D);
//		shop.setLatitude(1D);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("审核中");
        shop.setArea(area);
        shop.setShopCategory(sc);
        File shopImg = new File("C:\\Users\\·\\Desktop\\dingdan.png");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution se = shopService.addShop(shop,is,shopImg.getName());
        assertEquals(0,se.getState());
    }*/


    /*@Test
    public void  modifyShopTest(){
        Shop shop = new Shop();
        shop.setShopId();

    }
*/


    @Test
    public void testFile(){
        System.out.println(System.getProperty(File.separator));
    }


}
