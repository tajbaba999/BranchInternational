package com.example.branchinternational.data.model


fun Message.toEntity(): MessageEntity {
    return MessageEntity(
        id = this.id,
        thread_id = this.thread_id,
        user_id = this.user_id,
        body = this.body,
        timestamp = this.timestamp,
        agent_id = this.agent_id
    )
}

fun MessageEntity.toMessage(): Message {
    return Message(
        id = this.id,
        thread_id = this.thread_id,
        user_id = this.user_id,
        body = this.body,
        timestamp = this.timestamp,
        agent_id = this.agent_id
    )
}
