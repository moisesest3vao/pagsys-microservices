package br.com.pagsys.payment.clients;

import br.com.pagsys.payment.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(value = "msusers", path="/users")
public interface UserClient {
    @GetMapping("/getcurrent")
    UserDto getUserByToken(@RequestHeader("Authorization") String bearerToken);
    @GetMapping("/{id}")
    UserDto getUserBySub(@PathVariable("id") String id);
}
