package reference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tddstudy.repository.User;

import static org.mockito.Mockito.*;

public class Step1 {


    @Test
    @DisplayName("1. Mock 객체 만들고 verify 사용해보기")
    public void test() {

        /* 사전에 만들어놓은 User mock 객체를 생성합니다. (위치 : main -> org.tddstudy.repository.User ) */
        User user1 = mock(User.class);

        user1.setId("AAAAA"); //MOCK객체에 함수를 사용

        verify(user1).setId("ㅁㅁㅁ"); //error
         /*
         verify는 해당 mock 객체의 setId() 메서드가 호출되었는지, 그리고 요구하는 파라미터를 argument에 넣었는지 체크를 해준다.

         검증하길 바라는 것 : setId() 를 호출했는가? 파라미터로 "AAAAA"가 들어왔는가?
         실제 : setId()를 호출하였다. 파라미터로 "ㅁㅁㅁ" 를 입력하였다.
         검증과 실제가 달라 verify 실패 및 테스트가 종료된다.
         */

    }
}
