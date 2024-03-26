package org.itheima.reggie.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.itheima.reggie.common.BaseContext;
import org.itheima.reggie.common.R;
import org.itheima.reggie.entity.Employee;
import org.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /*
     * 管理员登录
     * */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(objectLambdaQueryWrapper);

        if (emp == null) {
            return R.error("登陆失败");
        }
        if (!emp.getPassword().equals(password)) {
            return R.error("登陆失败");
        }
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }
        HttpSession session = request.getSession();
        session.setAttribute("employee", emp.getId());
        return R.success(emp);
    }


    /*
     * 管理员退出
     * */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /*
     *
     * 新增员工
     * */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工");

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
          employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /*
     * 分页查询
     * */
    @GetMapping("page")
    public R<Page> page(int page, int pageSize, String name) {


        log.info("page={},pageSize={},name={}", page, pageSize, name);
        Page<Employee> page1 = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        Page page2 = employeeService.page(page1, lambdaQueryWrapper);
        return R.success(page2);
    }


    /*
    *
    * 根据id修改信息
    * */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        Long employee1 = (Long)request.getSession().getAttribute("employee");
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }
/*
* 根据ID查询用户*/
    @GetMapping("/{id}")
    public R<Employee> getId(@PathVariable long id){

log.info(""+id);
        Employee byId = employeeService.getById(id);
        if (byId != null) {
            return R.success(byId);
        }

        return R.error("没有查询到id");
    }



}



