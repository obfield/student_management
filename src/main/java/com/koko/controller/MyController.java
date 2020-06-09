package com.koko.controller;

import com.github.pagehelper.Page;
import com.koko.constant.JwtConstant;
import com.koko.constant.RedisConstant;
import com.koko.constant.StatusCode;
import com.koko.dao.UserDao;
import com.koko.dao.UserRoleDao;
import com.koko.dto.AddUser;
import com.koko.dto.PageInfo;
import com.koko.dto.ResponseBean;
import com.koko.dto.StudentScore;
import com.koko.entity.Grade;
import com.koko.entity.Subject;
import com.koko.entity.User;
import com.koko.exception.CustomException;
import com.koko.service.CommonService;
import com.koko.service.GradeService;
import com.koko.service.SubjectService;
import com.koko.service.UserService;
import com.koko.util.JWTUtil;
import com.koko.util.RedisClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Api("所有方法的操作接口")
@RestController
public class MyController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GradeService gradeService;


    @Value("${config.refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    private RedisClient redis;

    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账户", defaultValue = "2017020155", required = true),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "123456", required = true)
    })
    @PostMapping({"/", "/login"})
    public ResponseBean login(String account, String password, HttpServletResponse response) {

        try {
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("account", Integer.parseInt(account));
            User user = userDao.selectOneByExample(example);
//            User user = commonService.findUserByAccount(Integer.parseInt(account));
            if (user == null || !user.getPassword().equals(password)) {
                return new ResponseBean(StatusCode.ERROR, "用户名或密码错误", null);
            }
            if (redis.hasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
                redis.del(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account);
            }
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            redis.set(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account, currentTimeMillis,
                    Integer.parseInt(refreshTokenExpireTime));
            String token = JWTUtil.sign(account, currentTimeMillis);
            response.setHeader("Authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");

            return new ResponseBean(StatusCode.OK, "成功登录", null);
        } catch (Exception e) {
            return new ResponseBean(StatusCode.ERROR, e.getMessage(), null);
        }
    }

    @RequiresAuthentication
    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public ResponseBean logout(HttpServletRequest request) {
        try {
            String token = null;
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = request.getHeader(key);
                //取出的headername为小写，所以需要使用equalsIgnoreCase
                if ("Authorization".equalsIgnoreCase(key)) {
                    token = value;
                }
            }
            if (token == null) {
                return new ResponseBean(StatusCode.ERROR, "token为空", null);
            }
            String account = JWTUtil.getClaim(token, JwtConstant.ACCOUNT);
            if (account == null) {
                return new ResponseBean(StatusCode.ERROR, "token失效或错误", null);
            }
            if (redis.hasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
                redis.del(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account);
            }
            return new ResponseBean(StatusCode.OK, "成功退出", null);
        } catch (Exception e) {
            return new ResponseBean(StatusCode.ERROR, e.getMessage(), null);
        }
    }

    /**
     * 1:student
     * 2:teacher
     * 3:administrator
     */
    @RequiresAuthentication
    @ApiOperation("通过身份获取用户列表")
    @ApiImplicitParam(name = "job", value = "身份", required = true)
    @GetMapping("/users")
    public ResponseBean Users(@RequestBody Map<String, Object> params) {
        int job = (int) params.get("job");
        List<User> users = commonService.findAllUserByJob(job);
        return new ResponseBean().ok(users);
    }

    @RequiresAuthentication
    @ApiOperation("获取分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页数(第几页)", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的记录数", required = true)
    })
    @GetMapping("/pages")
    public ResponseBean Pages(@RequestBody Map<String, Object> params) {
        int pageNum = (int) params.get("pageNum");
        int pageSize = (int) params.get("pageSize");
        Page<User> pages = commonService.findAll(pageNum, pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(pages);
        return new ResponseBean().ok(pageInfo);
    }

    @RequiresAuthentication
    @ApiOperation("新增用户")
    @ApiImplicitParam(name = "addUser", value = "用户信息传输类", required = true)
    @PostMapping("/addUser")
    public ResponseBean addUser(@RequestBody AddUser addUser) {
        if (ObjectUtils.isEmpty(addUser)) {
            return new ResponseBean(StatusCode.ERROR, "user为空");
        }
        try {
            userService.addUser(addUser);
            return new ResponseBean(StatusCode.OK, "新增用户成功");
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR, e.getMessage());
        }
    }

    @RequiresAuthentication
    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "account", value = "账号", required = true)
    @PostMapping("/delUser")
    public ResponseBean delUser(@RequestBody Map<String, Object> params) {
        Object account = params.get("account");
        if (ObjectUtils.isEmpty(account)){
            return new ResponseBean(StatusCode.OK, "account为空");
        }
        try {
            userService.delUser((int)account);
            return new ResponseBean(StatusCode.OK, "删除用户成功");
        }catch (CustomException e){
            return new ResponseBean(StatusCode.OK, "删除用户失败");
        }
    }

    @RequiresAuthentication
    @ApiOperation("修改用户")
    @ApiImplicitParam(name = "addUser", value = "用户信息传输类", required = true)
    @PostMapping("/updateUser")
    public ResponseBean updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return new ResponseBean(StatusCode.OK, "更新成功");
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR, e.getMessage());
        }
    }

    @RequiresAuthentication
    @ApiOperation("查询用户")
    @ApiImplicitParam(name = "account", value = "账号", required = true)
    @PostMapping("/selectUser")
    public ResponseBean selectUser(@RequestBody Map<String, Object> params) {
        Object account = params.get("account");
        if (ObjectUtils.isEmpty(account)){
            return new ResponseBean(StatusCode.OK, "account为空");
        }
        try {
            User user = userService.selectUser((int) account);
            return new ResponseBean().ok(user);
        }catch (CustomException e){
            return new ResponseBean(StatusCode.OK, "删除用户失败");
        }
    }


    @RequiresAuthentication
    @ApiOperation("删除课程")
    @ApiImplicitParam(name = "subjectName", value = "课程名", required = true)
    @PostMapping("/delSubject")
    public ResponseBean delSubject(@RequestBody Map<String, Object> params){
        String subjectName = (String) params.get("subjectName");
        try {
            subjectService.delSubject(subjectName);
            return new ResponseBean(StatusCode.OK,"删除课程成功");
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR,e.getMessage());
        }
    }

    @RequiresAuthentication
    @ApiOperation("增加课程")
    @ApiImplicitParam(name = "subjectName", value = "课程名", required = true)
    @PostMapping("/addSubject")
    public ResponseBean addSubject(@RequestBody Map<String, Object> params){
        String subjectName = (String) params.get("subjectName");
        try {
            subjectService.addSubject(subjectName);
            return new ResponseBean(StatusCode.OK,"增加课程成功");
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR,e.getMessage());
        }
    }

    @RequiresAuthentication
    @ApiOperation("查询所有课程")
    @PostMapping("/selectAllSubject")
    public ResponseBean selectAllSubject(){
        try {
            List<Subject> subjects = subjectService.selectAllSubject();
            return new ResponseBean().ok(subjects);
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR,e.getMessage());

        }
    }

    @RequiresAuthentication
    @ApiOperation("增加用户课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true),
            @ApiImplicitParam(name = "subjectName", value = "课程名", required = true)
    })
    @PostMapping("/addSubjectByAccount")
    public ResponseBean addSubjectByAccount(@RequestBody Map<String, Object> params){
        int account = (int) params.get("account");
        String subjectName = (String) params.get("subjectName");
        try {
            subjectService.addSubjectByAccount(account, subjectName);
            return new ResponseBean(StatusCode.OK,"增加用户课程成功");
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR,e.getMessage());
        }
    }

    @RequiresAuthentication
    @ApiOperation("删除用户课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true),
            @ApiImplicitParam(name = "subjectName", value = "课程名", required = true)
    })
    @PostMapping("/delSubjectByAccount")
    public ResponseBean delSubjectByAccount(@RequestBody Map<String, Object> params){
        int account = (int) params.get("account");
        String subjectName = (String) params.get("subjectName");
        try {
            subjectService.delSubjectByAccount(account, subjectName);
            return new ResponseBean(StatusCode.OK,"删除用户课程成功");
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR,e.getMessage());
        }
    }

    @RequiresAuthentication
    @ApiOperation("新增成绩")
    @ApiImplicitParam(name = "grade", value = "grade(成绩)类", required = true)
    @PostMapping("/addGrade")
    public ResponseBean addGrade(@RequestBody Grade grade) {
        if (ObjectUtils.isEmpty(grade)) {
            return new ResponseBean(StatusCode.ERROR, "grade为空");
        }
        try {
            gradeService.addGrade(grade);
            return new ResponseBean(StatusCode.OK, "新增成绩成功");
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR, e.getMessage());
        }
    }

    @RequiresAuthentication
    @ApiOperation("删除成绩")
    @ApiImplicitParam(name = "grade", value = "grade(成绩)类", required = true)
    @PostMapping("/delGrade")
    public ResponseBean delGrade(@RequestBody Grade grade) {
        if (ObjectUtils.isEmpty(grade)) {
            return new ResponseBean(StatusCode.ERROR, "grade为空");
        }
        try {
            gradeService.delGrade(grade);
            return new ResponseBean(StatusCode.OK, "删除成绩成功");
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR, e.getMessage());
        }
    }


    @RequiresAuthentication
    @ApiOperation("更新成绩")
    @ApiImplicitParam(name = "grade", value = "grade(成绩)类", required = true)
    @PostMapping("/updateGrade")
    public ResponseBean updateGrade(@RequestBody Grade grade) {
        if (ObjectUtils.isEmpty(grade)) {
            return new ResponseBean(StatusCode.ERROR, "grade为空");
        }
        try {
            gradeService.updateGrade(grade);
            return new ResponseBean(StatusCode.OK, "更新成绩成功");
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR, e.getMessage());
        }
    }

    @RequiresAuthentication
    @ApiOperation("根据账号查询成绩")
    @ApiImplicitParam(name = "account", value = "账号", required = true)
    @PostMapping("/selectGradeByAccount")
    public ResponseBean selectGradeByAccount(@RequestBody Map<String, Object> params) {
        int account = (int) params.get("account");
        try {
            List<StudentScore> studentScores = gradeService.selectGradeByAccount(account);
            return new ResponseBean().ok(studentScores);
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR, e.getMessage());
        }
    }

    @RequiresAuthentication
    @ApiOperation("根据课程查询成绩")
    @ApiImplicitParam(name = "grade", value = "grade(成绩)类", required = true)
    @PostMapping("/selectGradeBySubjectName")
    public ResponseBean selectGradeBySubjectName(@RequestBody Map<String, Object> params) {
        String subjectName = (String) params.get("subjectName");
        try {
            List<StudentScore> studentScores = gradeService.selectGradeBySubjectName(subjectName);
            return new ResponseBean().ok(studentScores);
        }catch (CustomException e){
            return new ResponseBean(StatusCode.ERROR, e.getMessage());
        }
    }

}
