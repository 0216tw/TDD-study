package reference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.tddstudy.repository.User;
import org.tddstudy.repository.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class Step7 {
    @Test
    @DisplayName("7. verify 검증 & 호출 테스트")
    public void test() {

        List<User> userList = new ArrayList<>();
        userList.add(new User("AAAAA" , "pass123" , "내이름은A" , "010-1111-1111"));
        userList.add(new User("BBBBB" , "pass456" , "내이름은B" , "010-2222-2222"));
        userList.add(new User("CCCCC" , "pass789" , "내이름은C" , "010-3333-3333"));

        Users users = mock(Users.class); //테스트할 대상 목킹 (★★★ mocking 해야 when .. then 등 처리 가능함)

        users.findById(anyString());
        verify(users).findById(anyString()); //통과
        verify(users , never()).hasPasswordDollarWord(anyString() , anyString()); //통과
    }
}
