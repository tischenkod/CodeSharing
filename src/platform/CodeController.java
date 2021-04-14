package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CodeController {

    @Autowired
    CodeRepository repository;

    Code getCode(String id) {
        Code code = repository.findCode(id);
        if (code == null) {
            return null;
        }
        if (code.views > 0) {
            code.views--;
            if (code.views == 0) {
                code.views = null;
            }
            repository.save(code);
        }
        if (code.time > 0) {
            code.time = Math.toIntExact(code.date.toEpochSecond(ZoneOffset.UTC) + code.time - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        }
        return code;
    }

    @GetMapping("/code/{id}")
    public ModelAndView getCodeHTML(@PathVariable String id) {
        Code code = getCode(id);
        if (code == null) {
            ModelAndView response = new ModelAndView("/404");
            response.setStatus(HttpStatus.NOT_FOUND);
            return response;
        }

        var params = Map.of("code", code);
        return new ModelAndView("getCode", params);
    }

    @GetMapping("/api/code/{id}")
    ResponseEntity<Code> getCodeJSON(@PathVariable String id) {
        Code code = getCode(id);
        if (code == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(code);
    }

    @PostMapping("/api/code/new")
    ResponseEntity<NewCodeResponse> newCodeJSON(@RequestBody Code code) {
        code.init();
        repository.save(code);
        NewCodeResponse response = new NewCodeResponse(code.getUuid());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/new")
    ModelAndView newCodeHTML() {
        return new ModelAndView("newCode");
    }

    @GetMapping("/api/code/latest")
    ResponseEntity<List<Code>> getLatestJSON() {
        return ResponseEntity.ok(repository.latestCode());
    }

    @GetMapping("/code/latest")
    ModelAndView getLatestHTML() {
        var params = new HashMap<String, Object>();
        params.put("codes", repository.latestCode());
        return new ModelAndView("latest", params);
    }
}
