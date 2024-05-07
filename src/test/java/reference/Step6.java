package reference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.tddstudy.repository.User;
import org.tddstudy.repository.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class Step6 {
    @Test
    @DisplayName("6. 검증을 순서대로 하기 InOrder 이용")
    public void test() {

        List<User> userList = new ArrayList<>();
        userList.add(new User("AAAAA" , "pass123" , "내이름은A" , "010-1111-1111"));
        userList.add(new User("BBBBB" , "pass456" , "내이름은B" , "010-2222-2222"));
        userList.add(new User("CCCCC" , "pass789" , "내이름은C" , "010-3333-3333"));

        Users users = mock(Users.class); //테스트할 대상 목킹 (★★★ mocking 해야 when .. then 등 처리 가능함)


        /*
        * 어떤 로직이 순서대로 처리가 되었는지 InOrder 클래스를 이용해 줄세우기를 할 수 있다.
        * 만약 A 메서드 안에 B,C,D 메서드가 있다면 , A메서드를 inOrder에 세팅하고 순서대로 B,C,D 가 호출되는지 세팅도 가능하다
        * 예를 들어 다음과 같은 순서로 메서드가 실행되어야 한다고 한다면, 이 순서 검증이 가능하다는 의미이다.
        * (1) users에서 id 가 AAAAA인 대상을 가져오기
        * (2) AAAAA인 대상의 name을 hello로 변경하기
        * (3) AAAAA의 핸드폰을 010-6666-6666으로 변경하기
        * */

        //findById()를 호출하면 userList.get(0)의 값이 리턴되도록 세팅
        doAnswer(invocation -> {
            return userList.get(0);
        }).when(users).findById(anyString());

        //실제 순서대로 호출하기
        User foundUser = users.findById("AAAAA");
        users.setName(foundUser.getId() , "hello");
        users.setPhone(foundUser.getId() , "010-6666-6666");

        //일부러 순서 틀리게 해보기 (이 경우 실행 순서가 아래 order와 달라 에러가 발생, 테스트시 아래 주석 해제, 위의 호출은 주석처리 하세요
        //User foundUser2 = users.findById("AAAAA");
        //users.setPhone(foundUser2.getId() , "010-6666-6666");
        //users.setName(foundUser2.getId() , "hello");


        InOrder inOrder = inOrder(users); //users는 현재 mocking된 객체이다.


        inOrder.verify(users).findById("AAAAA"); // (1)
        inOrder.verify(users).setName("AAAAA" , "hello"); //(2)
        inOrder.verify(users).setPhone("AAAAA" , "010-6666-6666"); //(3)
    }
}
