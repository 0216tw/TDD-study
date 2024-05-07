package reference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tddstudy.repository.User;
import org.tddstudy.repository.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class Step5 {
    @Test
    @DisplayName("5. 반환값이 void 일때 반환값이나 예외 설정하기")
    public void test() {

        List<User> userList = new ArrayList<>();
        userList.add(new User("AAAAA" , "pass123" , "내이름은A" , "010-1111-1111"));
        userList.add(new User("BBBBB" , "pass456" , "내이름은B" , "010-2222-2222"));
        userList.add(new User("CCCCC" , "pass789" , "내이름은C" , "010-3333-3333"));

        Users users = mock(Users.class); //테스트할 대상 목킹 (★★★ mocking 해야 when .. then 등 처리 가능함)


        /*
        * 테스트할 메서드의 반환값이 void 일 경우 예외처리(doThrow) 나 응답처리(doAnswer) 를 할 수 있음
        * 예) users.setUsers( 파라미터 ) 는 출력값이 void 임
        * */
        doThrow(new RuntimeException()).when(users).setUsers(userList); //void 반환인 setUsers를 호출시 예외처리하도록 세팅

        try {
            users.setUsers(userList); //호출!
        } catch (RuntimeException e) {
            System.out.println("예외 캐치 완료!");

            //이번에는 doAnswer 테스트
            //invocation : 호출시 , 그리고 invocation.getArgument(0) 등을 통해 호출 당시 인자값을 가져올 수 있음
            //만약 메서드의 반환값이 존재한다면 return 부분에 매칭되는 반환타입 데이터를 리턴할 수 있음
            doAnswer( invocation -> {
                System.out.println("호출이 되었어요");
                return null;
            } ).when(users).setUsers(anyList());

            users.setUsers(userList);
        }
    }
}
