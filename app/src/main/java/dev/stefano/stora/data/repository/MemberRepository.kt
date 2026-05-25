package dev.stefano.stora.data.repository

import dev.stefano.stora.data.model.Member
import dev.stefano.stora.data.model.MemberDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MemberRepository {
    fun getAllMembers(): Flow<List<Member>>
    suspend fun insertMember(member: Member)
    suspend fun updateMember(member: Member)
    suspend fun deleteMember(member: Member)
}

class MemberRepositoryImpl @Inject constructor(
    private val memberDao: MemberDao
) : MemberRepository {
    override fun getAllMembers(): Flow<List<Member>> = memberDao.getAllMembers()
    override suspend fun insertMember(member: Member) = memberDao.insertMember(member)
    override suspend fun updateMember(member: Member) = memberDao.updateMember(member)
    override suspend fun deleteMember(member: Member) = memberDao.deleteMember(member)
}
