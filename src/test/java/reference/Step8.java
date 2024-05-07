package reference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tddstudy.repository.User;
import org.tddstudy.repository.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class Step8 {
    @Test
    @DisplayName("8. 불필요한 메서드 호출을 찾기 (or 메서드를 사용하지 않았는지 확인) ")
    public void test() {

        /*
        verifyNoMoreInteractions(mock) 는 해당 mock에 대해 메서드 호출이 발생하였는지를 확인한다.
        한번이라도 mock에 대한 메서드가 호출되면 위 코드는 에러를 발생시킨다.
        즉 never() 와 같은 개념이고,
        레퍼런스에서는 verifyNoMoreInteraction 보다는 never 를 사용하는 것을 더 권장한다.
         */

        List<User> userList = new ArrayList<>();
        userList.add(new User("AAAAA" , "pass123" , "내이름은A" , "010-1111-1111"));
        userList.add(new User("BBBBB" , "pass456" , "내이름은B" , "010-2222-2222"));
        userList.add(new User("CCCCC" , "pass789" , "내이름은C" , "010-3333-3333"));

        Users users = mock(Users.class); //테스트할 대상 목킹 (★★★ mocking 해야 when .. then 등 처리 가능함)

        verifyNoInteractions(users);  //users 관련하여 메서드 호출된것이 현재 시점에는 없다!! 그러므로 이 부분은 통과한다.

        users.findById("회원찾기호출1"); //users 메서드 호출1
        users.findById("회원찾기호출2"); //users 메서드 호출2

        verify(users).findById("회원찾기호출1"); //이 파라미터로 메서드가 호출되었는가? YES!

        //verify(users , never()).findById("회원찾기호출1"); //ERROR 이 메서드는 호출되었음

        //만약 위의 never()을 사용하지 않고
        //여기에 verifyNoInteractions(users) 를 한다고 해도 에러가 발생한다.
        //그 이유는 위에서 users 에 어떤 메서드 호출이 발생했기 때문이다. (이 경우 findById)
    }
}
