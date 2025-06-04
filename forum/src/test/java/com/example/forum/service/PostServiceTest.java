//package com.example.forum.service;
//
//import com.example.forum.dto.PostRequestDto;
//import com.example.forum.dto.PostResponseDto;
//import com.example.forum.exception.ResourceNotFoundException;
//import com.example.forum.model.Category;
//import com.example.forum.model.Post;
//import com.example.forum.repository.CategoryRepository;
//import com.example.forum.repository.PostRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class PostServiceTest {
//
//    private PostRepository postRepository;
//    private CategoryRepository categoryRepository;
//    private PostService postService;
//
////    @BeforeEach
////    void setUp() {
////        postRepository = mock(PostRepository.class);
////        categoryRepository = mock(CategoryRepository.class);
////        postService = new PostService(postRepository, categoryRepository);
////    }
//
//    @Test
//    void getPostsByCategory_ReturnsListOfPosts() {
//        // given
//        Long categoryId = 1L;
//        Post post = Post.builder()
//                .id(1L)
//                .title("Title")
//                .content("Content")
//                .author("Author")
//                .category(Category.builder().id(categoryId).name("Guides").build())
//                .build();
//        when(postRepository.findByCategoryId(categoryId)).thenReturn(Collections.singletonList(post));
//
//        // when
//        var result = postService.getPostsByCategory(categoryId);
//
//        // then
//        assertEquals(1, result.size());
//        assertEquals("Title", result.get(0).getTitle());
//        assertEquals("Guides", result.get(0).getCategory().getName());
//    }
//
//    @Test
//    void getPost_ReturnsPostResponseDto_WhenFound() {
//        Long postId = 2L;
//        Post post = Post.builder()
//                .id(postId)
//                .title("Test post")
//                .content("Text")
//                .author("KukyoUmi")
//                .category(Category.builder().id(3L).name("QA").build())
//                .build();
//        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
//
//        var dto = postService.getPost(postId);
//        assertEquals(postId, dto.getId());
//        assertEquals("Test post", dto.getTitle());
//        assertEquals("QA", dto.getCategory().getName());
//    }
//
//    @Test
//    void getPost_ThrowsException_WhenNotFound() {
//        Long postId = 99L;
//        when(postRepository.findById(postId)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> postService.getPost(postId));
//    }
//
//    @Test
//    void createPost_Success() {
//        // given
//        Long categoryId = 5L;
//        PostRequestDto req = new PostRequestDto();
//        req.setTitle("New post");
//        req.setContent("Text");
//        req.setAuthor("KukyoUmi");
//        req.setCategoryId(categoryId);
//
//        Category category = Category.builder().id(categoryId).name("News").build();
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
//        when(postRepository.save(any(Post.class))).thenAnswer(inv -> {
//            Post p = inv.getArgument(0);
//            p.setId(42L); // имитируем сохранение и проставку id
//            return p;
//        });
//
//        // when
//        PostResponseDto dto = postService.createPost(req);
//
//        // then
//        assertNotNull(dto.getId());
//        assertEquals("New post", dto.getTitle());
//        assertEquals("News", dto.getCategory().getName());
//    }
//
//    @Test
//    void createPost_ThrowsException_IfCategoryNotFound() {
//        Long catId = 8L;
//        PostRequestDto req = new PostRequestDto();
//        req.setTitle("No cat");
//        req.setContent("Test");
//        req.setAuthor("KukyoUmi");
//        req.setCategoryId(catId);
//
//        when(categoryRepository.findById(catId)).thenReturn(Optional.empty());
//
//        assertThrows(IllegalArgumentException.class, () -> postService.createPost(req));
//    }
//}
