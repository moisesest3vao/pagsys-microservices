package br.com.pagsys.payment.clients;

import br.com.pagsys.payment.dto.UserDto;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(value = "msusers", path="/users/getcurrent")
public interface UserClient {
    @GetMapping
    public UserDto getUserByToken(@RequestHeader("Authorization") String bearerToken);
}
