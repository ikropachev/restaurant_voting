package org.ivan_kropachev.restaurant_voting.web.vote;

import org.ivan_kropachev.restaurant_voting.AuthorizedUser;
import org.ivan_kropachev.restaurant_voting.model.Dish;
import org.ivan_kropachev.restaurant_voting.model.User;
import org.ivan_kropachev.restaurant_voting.model.Vote;
import org.ivan_kropachev.restaurant_voting.util.exception.LateVoteException;
import org.ivan_kropachev.restaurant_voting.web.user.AdminUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.ivan_kropachev.restaurant_voting.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController extends AbstractVoteController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/admin/votes";

    @Override
    @GetMapping
    public List<Vote> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        return super.get(id);
    }

    //add userId here
    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote createWithLocation(@PathVariable int restaurantId) {
        log.info("try to create/update vote from user with id {}", authUserId());
        //int userId = 100005;
        if (LocalTime.now().isAfter(LocalTime.of(23, 00))) {
            throw new LateVoteException("Too late for voting");
        }
        //Vote vote = new Vote(null, userId, restaurantId, LocalDate.now());
        //Vote created = super.create(vote);
        //URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
        //        .path(REST_URL + "/{id}")
        //        .buildAndExpand(created.getId()).toUri();
        return super.create(authUserId(), restaurantId);
        //return super.create(vote);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Vote vote, @PathVariable int id) {
        super.update(vote, id);
    }
    /*
    @Override
    public List<Vote> getAll() {
        return super.getAll();
    }

    @Override
    public Vote get(int id) {
        return super.get(id);
    }

    @Override
    public Vote create(Vote vote) {
        return super.create(vote);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public void update(Vote vote, int id) {
        super.update(vote, id);
    }

     */
}
