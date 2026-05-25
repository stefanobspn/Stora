package dev.stefano.stora.ui.member

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stefano.stora.data.model.Member
import dev.stefano.stora.data.repository.MemberRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val repository: MemberRepository
) : ViewModel() {

    val allMembers: StateFlow<List<Member>> = repository.getAllMembers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var memberName by mutableStateOf("")
        private set
    var memberPhone by mutableStateOf("")
        private set
    var memberPoints by mutableStateOf("0")
        private set

    var editingMember by mutableStateOf<Member?>(null)
        private set

    fun onNameChange(newName: String) { memberName = newName }
    fun onPhoneChange(newPhone: String) { memberPhone = newPhone }
    fun onPointsChange(newPoints: String) { memberPoints = newPoints }

    fun setMemberForEditing(member: Member?) {
        editingMember = member
        if (member != null) {
            memberName = member.name
            memberPhone = member.phoneNumber
            memberPoints = member.points.toString()
        } else {
            clearForm()
        }
    }

    fun saveMember() {
        val points = memberPoints.toIntOrNull() ?: 0
        if (memberName.isNotBlank() && memberPhone.isNotBlank()) {
            viewModelScope.launch {
                val currentMember = editingMember
                if (currentMember == null) {
                    repository.insertMember(Member(name = memberName, phoneNumber = memberPhone, points = points))
                } else {
                    repository.updateMember(currentMember.copy(name = memberName, phoneNumber = memberPhone, points = points))
                }
                clearForm()
            }
        }
    }

    fun deleteMember() {
        editingMember?.let { member ->
            viewModelScope.launch {
                repository.deleteMember(member)
                clearForm()
            }
        }
    }

    fun clearForm() {
        memberName = ""
        memberPhone = ""
        memberPoints = "0"
        editingMember = null
    }
}
