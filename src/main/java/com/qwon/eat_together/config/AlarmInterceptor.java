package com.qwon.eat_together.config;

import com.qwon.eat_together.domain.Account;
import com.qwon.eat_together.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AlarmInterceptor implements HandlerInterceptor {

    private final AlarmRepository alarmRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        // 인증정보가 있는 요청에만 Alarm 제공
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(modelAndView!=null && !isRedirectView(modelAndView) &&
                authentication!=null && authentication.getPrincipal() instanceof UserAccount){
            Account account=((UserAccount)authentication.getPrincipal()).getAccount();
            long count=alarmRepository.countByAccountAndChecked(account, false);
            modelAndView.addObject("hasAlarm",count>0);
        }
    }

    private boolean isRedirectView(ModelAndView modelAndView) {
        return modelAndView.getViewName().startsWith("redirect:")
                || modelAndView.getView() instanceof RedirectView;
    }
}
