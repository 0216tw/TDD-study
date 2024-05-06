package reference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tddstudy.repository.User;

import static org.mockito.Mockito.*;

public class Step2 {

    @Test
    @DisplayName("2. stub기술: verify로 메서드 호출시 특정 반환값 세팅하기")
    public void createMock() {

        /* 사전에 만들어놓은 User mock 객체를 생성합니다. (위치 : main -> org.tddstudy.repository.User ) */
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        user1.setId("AAAAA");
        user1.setName("내이름은A");

        /* stub 방식
           => 특정 메서드를 호출하면 반환할 값을 지정할 수 있다.
              (예) user1.getId()가 호출되면 "AAAAA" 가 리턴되게 하자
           => 아래와 같이 when .. thenReturn 이나 when .. thenThrow 기법을 활용한다.  */
        when(user1.getId()).thenReturn("AAAAA");
        when(user1.getName()).thenThrow(new RuntimeException());


        //assertEquals : 두 값이 동일한지 확인
        Assertions.assertEquals("AAAAA" , user1.getId());
        // Assertions.assertEquals("AAAAB" , user1.getId()); //error발생 (두 값이 동일하지 않음)

        /* 아래 케이스는 정해진대로 new RuntimeException() 을 리턴한다.
           그런데 아예 CheckedException이 되었다!. (★★★)
           컴파일 시점에서 에러를 보여주니 테스트가 더 정확할 것 같다. */
        // Assertions.assertEquals("내이름은A" , user1.getName()); //error 발생 (왜냐면 위에서 메소드 호출시 new RuntimeException을 지정했음)

        // 이번에는 try - catch 를 감싸보기 ( 코드를 실행하려 하면 아예 CheckedException으로 처리해줌
        try {
            Assertions.assertEquals("내이름은A" , user1.getName());
        } catch (RuntimeException e) {
            System.out.println("뭔가 에러가 났어");
        }

        verify(user1).getName(); //위에서 정상적으로 호출했음이 입증되었다.
        verify(user1).getName(); //verity는 한번 검증했다고 끝이 아니라, 일단 호출했으면 여러번 검증이 가능하다.
        verify(user1).getPhone(); //error :  이건 호출안했기때문에 not invoked 에러가 나온다.

    }
}
