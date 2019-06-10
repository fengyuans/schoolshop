import com.my.schoolshop.dao.AreaDao;
import com.my.schoolshop.model.Area;
import com.my.schoolshop.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Autowired
    private AreaService service;


  /*  @Test
    public void queryAllTest(){

        List<Area> list = service.getAreaList();
        System.out.println(list);
    }*/
}
