package com.linkee.linkeeapi.bookmark.query.controller;

import com.linkee.linkeeapi.bookmark.query.dto.request.BookmarkSearchRequest;
import com.linkee.linkeeapi.bookmark.query.dto.response.BookmarkResponse;
import com.linkee.linkeeapi.bookmark.query.service.BookmarkQueryService;
import com.linkee.linkeeapi.common.model.PageResponse;
import com.linkee.linkeeapi.user.command.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkQueryController {

    private final BookmarkQueryService bookmarkQueryService;
    private final UserRepository userRepository;


    @GetMapping
    public ResponseEntity<PageResponse<BookmarkResponse>> getAllBookmarks(BookmarkSearchRequest request) {
        PageResponse<BookmarkResponse> response = bookmarkQueryService.selectAllBookmarks(request);

        return ResponseEntity.ok(response);
    }

    // 내 북마크 조회
    @GetMapping("/me")
    public ResponseEntity<PageResponse<BookmarkResponse>> getBookmarksByUserId(
            @AuthenticationPrincipal UserDetails userDetails,
            BookmarkSearchRequest request
    ) {
        String loginUserEmail = userDetails.getUsername();

        Long userId = userRepository.findByUserEmail(loginUserEmail).orElseThrow().getUserId();

        request.setUserId(userId);

        PageResponse<BookmarkResponse> response = bookmarkQueryService.selectBookmarksByUserId(request);

        return ResponseEntity.ok(response);
    }

}