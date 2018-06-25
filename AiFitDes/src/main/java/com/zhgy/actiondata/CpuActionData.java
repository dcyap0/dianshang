package com.zhgy.actiondata;

import com.zhgy.entity.CpuEntity;
import com.zhgy.service.CpuService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/cpu", method = RequestMethod.POST)
public class CpuActionData {

    private String help = "/**\n" +
            " * Class Name: CpuActionData\n" +
            " * 功能：该类提供了返回JSON格式的数据接口。\n" +
            " * 方法名：\n" +
            " *     1.getAllBrands();\n" +
            " *         访问地址：.../cpu/all_brands\n" +
            " *         携带参数：无\n" +
            " *         请求类型：POST\n" +
            " *         说明：该方法返回CPU的所有品牌。\n" +
            " *\n" +
            " *     2.getAllNameByBrand(brand_name)\n" +
            " *         访问地址：.../cpu/all_name_by_brand\n" +
            " *         携带参数：brand_name - 用于指明一个品牌的名称\n" +
            " *         请求类型：POST\n" +
            " *         说明：该方法返回 CPU 指定 品牌名 的所有 系列名\n" +
            " *\n" +
            " *     3.getAllModelByBrandAndName(brand_name, name)\n" +
            " *         访问地址：.../cpu/all_model_by_brand_name\n" +
            " *         携带参数：brand_name - 用于指明一个品牌的名称\n" +
            " *                   name - 用于指明一个品牌下的系列名\n" +
            " *         请求类型：POST\n" +
            " *         说明：该方法返回CPU中指定品牌下系列的所有产品名\n" +
            " *\n" +
            " *     4.getAllByPageNumAndSize(pageNum, pageSize)\n" +
            " *         访问地址：.../cpu/all_by_num_size\n" +
            " *         携带参数：pageNum - 需要返回的当前页数值\n" +
            " *                   pageSize - 当前页面的数据容量\n" +
            " *         请求类型：POST\n" +
            " *         说明：该方法返回无选择条件的CPU信息\n" +
            " *\n" +
            " *     5.getAllByWherePageNumAndSize(where_context, pageNum, pageSize)\n" +
            " *         访问地址：.../cpu/all_by_condition\n" +
            " *         携带参数：where_context - 数据筛选条件，如（price<1000 and price not like '%万%'）\n" +
            " *                   pageNum - 需要返回的当前页数值\n" +
            " *                   pageSize - 当前页面的数据容量\n" +
            " *         请求类型：POST\n" +
            " *         说明：该方法返回按照选择的条件返回相应的CPU信息\n" +
            " *\n" +
            " *     6.deleteById(id)\n" +
            " *         访问地址：.../cpu/delete_by_id\n" +
            " *         携带参数：id - 需要删除的id\n" +
            " *         请求类型：POST\n" +
            " *         说明：该方法返回一个字符串，字符串的格式为 信号#原因 例：success#删除成功！或 error#删除失败！原因。。。\n" +
            " *               开发者使用时首先对返回值进行split('#')处理，先判断split('#')[0]的状态，在输出split('#')[1]中的提示。\n" +
            " *               split('#')[0] = success为删除成功，split('#')[1] = error为删除失败\n" +
            " *\n" +
            " *     7.getOneById(id)\n" +
            " *         访问地址： .../cpu/get_one_by_id\n" +
            " *         携带参数：id - 需要查询的id\n" +
            " *         请求类型：POST\n" +
            " *         说明：该方法按照ID返回一个CPU的信息。如没有找到相关数据，返回空JSON列表。\n" +
            " *\n" +
            " * */";

    @Autowired
    private CpuService cpuService;

    @ResponseBody
    @RequestMapping(value = "/all_brands", method = RequestMethod.POST)
    public String getAllBrands(Model model){
        JSONArray jsonArray = new JSONArray();
        List<String> list = cpuService.getAllBrandsName();
        for(int i=0; i<list.size(); i++){
            JSONObject jo = new JSONObject();
            jo.put("BrandName", list.get(i));
            jsonArray.put(jo);
        }
        return jsonArray.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/all_name_by_brand", method = RequestMethod.POST)
    public String getAllNameByBrand(@RequestParam("brand_name") String brand_name, Model model){
        JSONArray jsonArray = new JSONArray();
        List<String> list = cpuService.getAllNameByBrand(brand_name);
        for(int i=0; i<list.size(); i++){
            JSONObject jo = new JSONObject();
            jo.put("Name", list.get(i));
            jsonArray.put(jo);
        }
        return jsonArray.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/all_model_by_brand_name", method = RequestMethod.POST)
    public String getAllModelByBrandAndName(@RequestParam("brand_name") String brand_name,
                                            @RequestParam("name") String name,
                                            Model model){
        JSONArray jsonArray = new JSONArray();
        List<String> list = cpuService.getAllModelByBrandAndName(brand_name, name);
        for(int i=0; i<list.size(); i++){
            JSONObject jo = new JSONObject();
            jo.put("Model", list.get(i));
            jsonArray.put(jo);
        }
        return jsonArray.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/all_by_num_size", method = RequestMethod.POST)
    public String getAllByPageNumAndSize(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize,
                                         Model model){
        JSONArray jsonArray = new JSONArray();
        List<CpuEntity> list = cpuService.getAllByPageNumAndSize(pageNum, pageSize);
        for(int i=0; i<list.size(); i++){
            JSONObject jo = new JSONObject();
            CpuEntity ce = list.get(i);
            jo.put("id", ce.getId());
            jo.put("brands", ce.getBrands());
            jo.put("name", ce.getName());
            jo.put("model", ce.getModel());
            jo.put("price", ce.getPrice());
            jo.put("applicable_type", ce.getApplicableType());
            jo.put("cpu_series", ce.getCpuSeries());
            jo.put("cpu_main_frequency", ce.getCpuMainFrequency());
            jo.put("ghz", ce.getGhz());
            jo.put("slot_type", ce.getSlotType());
            jo.put("two_level_caching", ce.getTwoLevelCaching());
            jo.put("core_quantity", ce.getCoreQuantity());
            jo.put("thread_number", ce.getThreadNumber());
            jo.put("img", ce.getImg());
            jsonArray.put(jo);
        }
        return jsonArray.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/all_by_condition", method = RequestMethod.POST)
    public String getAllByWherePageNumAndSize(@RequestParam("where_context") String where_context,
                                               @RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize,
                                               Model model){
        JSONArray jsonArray = new JSONArray();
        List<CpuEntity> list = cpuService.getCPUByCondition(where_context, pageNum, pageSize);
        for(int i=0; i<list.size(); i++){
            JSONObject jo = new JSONObject();
            CpuEntity ce = list.get(i);
            jo.put("id", ce.getId());
            jo.put("brands", ce.getBrands());
            jo.put("name", ce.getName());
            jo.put("model", ce.getModel());
            jo.put("price", ce.getPrice());
            jo.put("applicable_type", ce.getApplicableType());
            jo.put("cpu_series", ce.getCpuSeries());
            jo.put("cpu_main_frequency", ce.getCpuMainFrequency());
            jo.put("ghz", ce.getGhz());
            jo.put("slot_type", ce.getSlotType());
            jo.put("two_level_caching", ce.getTwoLevelCaching());
            jo.put("core_quantity", ce.getCoreQuantity());
            jo.put("thread_number", ce.getThreadNumber());
            jo.put("img", ce.getImg());
            jsonArray.put(jo);
        }
        return jsonArray.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/delete_by_id", method = RequestMethod.POST)
    public String deleteById(@RequestParam("id") String id, Model model){
        cpuService.deleteById(id);
        if(cpuService.getCPUById(id) == null){
            return "success#删除成功！";
        }
        return "error#删除失败！";
    }

    @ResponseBody
    @RequestMapping(value = "/get_one_by_id", method = RequestMethod.POST)
    public String getOneById(@RequestParam("id") String id, Model model){
        CpuEntity ce = cpuService.getCPUById(id);
        JSONArray jsonArray = new JSONArray();
        JSONObject jo = new JSONObject();
        if(ce == null){
            jsonArray.put(jo);
            return jsonArray.toString();
        }
        jo.put("id", ce.getId());
        jo.put("brands", ce.getBrands());
        jo.put("name", ce.getName());
        jo.put("model", ce.getModel());
        jo.put("price", ce.getPrice());
        jo.put("applicable_type", ce.getApplicableType());
        jo.put("cpu_series", ce.getCpuSeries());
        jo.put("cpu_main_frequency", ce.getCpuMainFrequency());
        jo.put("ghz", ce.getGhz());
        jo.put("slot_type", ce.getSlotType());
        jo.put("two_level_caching", ce.getTwoLevelCaching());
        jo.put("core_quantity", ce.getCoreQuantity());
        jo.put("thread_number", ce.getThreadNumber());
        jo.put("img", ce.getImg());
        jsonArray.put(jo);
        return jsonArray.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String getHelpGet(){ return help; }

    @ResponseBody
    @RequestMapping(value = "/help", method = RequestMethod.POST)
    public String getHelpPost(){ return help; }
}
