package cn.lee.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName:LoginFilter
 * @Author：Mr.lee
 * @DATE：2020/04/08
 * @TIME： 9:47
 * @Description: TODO
 */
@Component
public class LoginFilter extends ZuulFilter {
    /**
     * 过滤器的类型：pre、route、post、error
     *pre 之前执行
     * post 之后执行
     * route 路由时执行
     * error 出错时执行
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 执行顺序，返回值越小，优先级越高
     *  pre-->route-->post --> 响应  Zuul的执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行run方法
     * true:执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 编写过滤的业务逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //初始context上下文,  servlet spring
        RequestContext context = RequestContext.getCurrentContext();
        //用来获取request对象
        HttpServletRequest request = context.getRequest();
        //使用request对象获取参数
        String token = request.getParameter("token");
        //如果token为null
        if (StringUtils.isEmpty(token)) {
            //拦截，不通过Zuul转发请求
            context.setSendZuulResponse(false);
            //响应状态码，401 省份未认证
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            //设置友好响应信息
            context.setResponseBody("request error!");
        }

        //返回null，代表过滤器什么都没做
        return null;
    }
}
