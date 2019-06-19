package life.antonio12.community12.community12.controller;

import life.antonio12.community12.community12.mapper.UserMapper;
import life.antonio12.community12.community12.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token"))
            {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if(user != null)
                {
                    request.getSession().setAttribute("user", user); // 访问首页时 去看所有的cookie（for) 找到cookie是token那个
                    // 拿着个cookie 放到 数据库里去查 是不是有这个cookie记录  如果有 就把user放到session里面 这样前端就可以判断是不是展示是我还是登陆
                }
                break;
            }
        }
        return "index";
    }
}
