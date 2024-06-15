package uit.se122.ieltstinder.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uit.se122.ieltstinder.repository.MessageRepository;
import uit.se122.ieltstinder.service.MessageService;
import uit.se122.ieltstinder.service.mapper.MessageMapper;
import uit.se122.ieltstinder.socket.dto.MessageResponse;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper mapper;

    @Override
    public Page<MessageResponse> getMessages(Long chatroomId, Pageable pageable) {
        return messageRepository.getMessageByChatroomId(chatroomId, pageable).map(mapper::toMessageResponse);
    }
}
