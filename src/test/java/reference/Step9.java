package reference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tddstudy.repository.User;
import org.tddstudy.repository.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/*
     @Mock 어노테이션의 장점
      (1) mock을 생성하는데 필요한 반복적인 코드를 최소화해준다.
      (2) 테스트 케이스의 가독성을 높여준다.
      (3) mock 식별에 필드이름을 사용하기 때문에 검증 오류를 더 쉽게 파악할 수 있다.

      < mock 어노테이션 사용 전 >
      private MyService myService ;
      myService = Mockito.mock(MyService.class);
      ...

      < mock 어노테이션 사용 후 >
      @Mock
      private MyService myService;

      MockitoAnnotations.initMocks(this); // @Mock 어노테이션이 달린 필드에 자동으로 모의 객체 할당
      */

      /*
            추가지식
            [Mock 와 Spy의 차이]
            Mock는 말그대로 가짜객체이기 때문에, 내부 메서드는 아무런 작동도 하지 않는다.
            작동을 하고 싶다면 우리가 when .. then 등으로 직접 세팅을 해줘야 한다.
            따라서 메서드 호출 자체에 의미가 있다면 Mock을 사용하면 된다.

            Spy는 객체가 가지는 특정 메서드에 대해 실제 인스턴스 동작을 하도록 할 수 있다.
            즉, 실제 객체의 모든 동작이 기본적으로 유지되며, 선택적으로 일부 메서드의 동작 변경이 가능하다
       */

public class Step9 {
    @Mock
    private Users users ; //Mock 어노테이션으로 users 관련 mock를 주입
    private List<User> userList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        // MockitoAnnotations.initMocks();  initMocks 는 depredicated 되었음! 대신 openMocks 사용
        MockitoAnnotations.openMocks(this); // Mock 가 설정된 객체 초기화
        userList.add(new User("AAAAA" , "pass123" , "내이름은A" , "010-1111-1111"));
        userList.add(new User("BBBBB" , "pass456" , "내이름은B" , "010-2222-2222"));
        userList.add(new User("CCCCC" , "pass789" , "내이름은C" , "010-3333-3333"));
        when(users.getUsers()).thenReturn(userList);
    }

    @Test
    @DisplayName("9. mock 객체들을 @Mock 어노테이션으로 축약하여 생성하기")
    public void test() {

        //users 의 setUsers() 기능을 써야하는데.. 이럴려면 Spy를 써야 실제 객체의 기능을 사용할 수 있음
        //하지만 이 예시는 @Mock 어노테이션이니까 꾸역..꾸역..써보겠음

        List<User> list = users.getUsers();
        System.out.println(list.get(0)); //데이터가 잘 세팅된 것을 볼 수 있다.


        //아무튼 여기서는 Mock 하고 싶은 객체들에 대해서
        //@Mock 어노테이션 처리 및 Mockito.openMocks(this) 을 통해 일괄 주입이 가능하다는 점을 주목하면 될거같다.
    }
}
