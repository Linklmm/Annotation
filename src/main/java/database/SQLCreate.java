package database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SQLCreate {
    public static void main(String[] args) throws Exception{
        Filter filter=new Filter();
        filter.setAge(1);
        filter.setCity("福建");
        filter.setEmail("1213131113@qq.com");
        filter.setId(2);
        filter.setNickName("你的花花");
        filter.setUserName("我的草草");
        filter.setMobile("15773313613");
        String[] strings={"database.Filter"};
        for (String className : strings) {
            Class<?> cl=Class.forName(className);//加载table类
            Table table=cl.getAnnotation(Table.class);//返回table的注解对象
            if(table==null){
                System.out.println("No Table annotations in class"+className);
                continue;
            }
            String tableName=table.value();//得到表的表名
            if (tableName.length()<1)
                tableName=cl.getName().toUpperCase();//如果表名为空，使用这个类的名

            List<String> columnDefs=new ArrayList<String>();
            //返回类中的所有属性，进行遍历
            for (Field field:cl.getDeclaredFields()) {
                String columnName = null;
                //返回Table类中的所有注解 anns数组中存有所有注解
                Annotation[] anns = field.getDeclaredAnnotations();
                if (anns.length < 1) {
                    System.out.println("TABLE中的字段为空！");
                    continue;//如果返回的数组为空，则表示Table中没有字段
                }
                //System.out.println(anns[0]);
                //得到字段名
                if (anns[0] instanceof Column) {
                    Column clm = (Column) anns[0];
                    //判断注解中的value是否为空，如果不为空字段名用value中的值，并将其添加进columnDefs数组中
                    if (clm.value().length() < 1)
                        columnName = field.getName().toUpperCase();
                    else
                        columnName = clm.value();
                    columnDefs.add(columnName);
                }
            }
                //生成sql语句
                StringBuilder createcomand=new StringBuilder("select * from "+tableName+" where ");
                //字段名赋值
            createcomand.append(columnDefs.get(0)+" = "+filter.getId()+" ");
            createcomand.append("and "+columnDefs.get(1)+" = "+filter.getUserName()+" ");
            createcomand.append("and "+columnDefs.get(2)+" = "+filter.getNickName()+" ");
            createcomand.append("and "+columnDefs.get(3)+" = "+filter.getAge()+" ");
            createcomand.append("and "+columnDefs.get(4)+" = "+filter.getCity()+" ");
            createcomand.append("and "+columnDefs.get(5)+" = "+filter.getEmail()+" ");
            createcomand.append("and "+columnDefs.get(6)+" = "+filter.getMobile()+" ");
//                for (String columnDef:columnDefs) {
//                    createcomand.append(columnDef+"=");
//                    createcomand.append(" and ");
//                }
                String select=createcomand.substring(0,createcomand.length()-1);
                System.out.println("SQL:\n"+select);

        }
    }
}
